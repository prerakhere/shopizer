# Local Setup Troubleshooting

## Prerequisites
- Java 11+ (tested with Java 21)
- Maven 3.x

## Run Command
```bash
cd sm-shop
./mvnw spring-boot:run -Dspring-boot.run.profiles=docker
```

Access: http://localhost:8080/swagger-ui.html  
Health: http://localhost:8080/actuator/health

---

## Issues & Fixes

### 1. `database.properties` not found
**Error:** `class path resource [database.properties] cannot be opened because it does not exist`  
**Cause:** Running without a Spring profile — the app expects profile-specific DB config.  
**Fix:** Always run with a profile (e.g., `docker`).

---

### 2. Mail properties unresolved
**Error:** `Could not resolve placeholder 'mailSender.protocol'`  
**Cause:** `shopizer-core-config.xml` registers `PropertyPlaceholderConfigurer` only for known profiles (`local`, `mysql`, `gcp`, `cloud`, `aws`). The `docker` profile was missing.  
**Fix:**
- Added `docker` profile block to `sm-core/src/main/resources/spring/shopizer-core-config.xml`
- Created `sm-core/src/main/resources/profiles/docker/shopizer-core.properties` with dummy mail config:
  ```properties
  mailSender.protocol=smtp
  mailSender.host=localhost
  mailSender.port=25
  mailSender.username=test
  mailSender.password=test
  mailSender.mail.smtp.auth=false
  mail.smtp.starttls.enable=false
  ```

---

### 3. `ProductFacade` bean not found
**Error:** `Field productFacade required a bean of type 'ProductFacade' that could not be found`  
**Cause:** `ProductFacadeImpl` and 3 other facade classes had `@Profile` annotations that didn't include `docker`:
```java
// Before
@Profile({ "default", "cloud", "gcp", "aws", "mysql", "local" })
```
**Fix:** Added `"docker"` to the `@Profile` list in:
- `ProductFacadeImpl.java`
- `ProductFacadeV2Impl.java`
- `ProductDefinitionFacadeImpl.java`
- `ProductInventoryFacadeImpl.java`

---

### 4. Port 8080 already in use
**Error:** `Web server failed to start. Port 8080 was already in use.`  
**Fix:**
```bash
lsof -ti:8080 | xargs kill -9
```

---

## Setup Checklist

- [ ] Java 11+ installed (`java -version`)
- [ ] Running from `sm-shop/` directory with `-Dspring-boot.run.profiles=docker`
- [ ] `sm-core/src/main/resources/spring/shopizer-core-config.xml` has a `docker` profile block
- [ ] `sm-core/src/main/resources/profiles/docker/shopizer-core.properties` exists
- [ ] `sm-shop/src/main/resources/profiles/docker/database.properties` exists (uses H2)
- [ ] All 4 product facade classes include `"docker"` in their `@Profile` annotation
- [ ] Port 8080 is free before starting
