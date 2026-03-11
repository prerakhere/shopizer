# Shopizer E-Commerce Platform - Project Documentation

## Overview

**Shopizer** is an open-source Java-based headless e-commerce platform that provides a complete REST API for building modern e-commerce applications. It's designed for Java 11+ (tested with Java 11 and 17) and follows a microservices-ready architecture.

**Current Version:** 3.2.5 (Latest: 3.2.7)  
**License:** Apache License 2.0  
**Official Site:** http://www.shopizer.com

## What is Shopizer?

Shopizer is a headless commerce solution that separates the backend business logic from the frontend presentation layer. This allows developers to:

- Build custom storefronts using any frontend technology (React, Vue, Angular, mobile apps, etc.)
- Access all e-commerce functionality through RESTful APIs
- Scale frontend and backend independently
- Create omnichannel shopping experiences

## Core Features

### E-Commerce Capabilities

1. **Catalog Management**
   - Product management with variants and options
   - Category hierarchies
   - Manufacturer/brand management
   - Product reviews and ratings
   - Inventory tracking

2. **Shopping Experience**
   - Shopping cart functionality
   - Checkout process
   - Multiple payment methods
   - Shipping calculations and methods
   - Tax management

3. **Order Management**
   - Order processing and tracking
   - Order status updates
   - Invoice generation
   - Digital product downloads

4. **Customer Management**
   - Customer accounts and profiles
   - Address management
   - Order history
   - Customer authentication

5. **Merchant/Store Management**
   - Multi-store support
   - Store configuration
   - User and permission management
   - Content management (CMS)

6. **Additional Features**
   - Search functionality (Elasticsearch integration)
   - Email notifications (templated)
   - Marketing capabilities
   - Tax calculation
   - Geolocation services

## Architecture

### Multi-Module Maven Project

Shopizer is organized as a Maven multi-module project with the following structure:

```
shopizer/
├── sm-core-model/          # Core domain models and entities
├── sm-core/                # Business logic and services
├── sm-core-modules/        # External module implementations
├── sm-shop-model/          # API model objects (DTOs)
└── sm-shop/                # REST API and web application
```

### Module Breakdown

#### 1. **sm-core-model**
- Contains JPA entity classes representing the domain model
- Database schema definitions
- Core business objects (Product, Order, Customer, etc.)
- Located at: `com.salesmanager.core.model`

**Key Packages:**
- `catalog` - Product, category, and catalog entities
- `customer` - Customer and customer-related entities
- `order` - Order and order-related entities
- `merchant` - Store/merchant entities
- `shipping` - Shipping methods and configurations
- `payments` - Payment processing entities
- `tax` - Tax calculation entities
- `content` - CMS content entities
- `user` - User and authentication entities

#### 2. **sm-core**
- Business logic layer
- Service implementations
- Data access repositories (Spring Data JPA)
- Integration modules (payment, shipping, email, etc.)
- Located at: `com.salesmanager.core`

**Key Features:**
- Spring Data JPA repositories
- Business service layer
- Email templating (Freemarker)
- Search integration (Elasticsearch)
- Cloud storage support (AWS, GCP)
- Multiple database profiles (H2, MySQL)

#### 3. **sm-core-modules**
- External module implementations
- Plugin architecture for extending functionality
- Integration with third-party services

#### 4. **sm-shop-model**
- API model objects (DTOs)
- Request/response objects for REST API
- Separates API contracts from domain models

#### 5. **sm-shop** (Main Application)
- Spring Boot application entry point
- REST API controllers
- API versioning (v0, v1, v2)
- Security configuration
- Swagger/OpenAPI documentation

**Key Packages:**
- `store.api.v1` - Version 1 REST API endpoints
- `store.facade` - Facade layer for business logic
- `store.security` - Authentication and authorization
- `mapper` - Object mapping between domain and API models
- `populator` - Data population utilities
- `filter` - Request/response filters (CORS, XSS)

### Technology Stack

**Core Framework:**
- Spring Boot 2.5.12
- Spring Data JPA
- Hibernate ORM

**Database:**
- H2 (default, embedded)
- MySQL (production-ready profile)
- PostgreSQL support

**Search:**
- Elasticsearch 7.5.2

**Security:**
- Spring Security
- JWT authentication support

**API Documentation:**
- Swagger/OpenAPI (Springfox)

**Template Engine:**
- Freemarker (for emails and invoices)

**Build Tool:**
- Maven 3.x

**Cloud Support:**
- AWS S3 for file storage
- Google Cloud Platform (GCP) support
- Cloud-native configuration profiles

## API Structure

### API Versioning

Shopizer uses URL-based API versioning:

- **v0** - Legacy/deprecated endpoints
- **v1** - Current stable API (primary)
- **v2** - Next generation API (in development)

### Main API Endpoints (v1)

```
/api/v1/
├── products/              # Product catalog
├── categories/            # Category management
├── cart/                  # Shopping cart
├── checkout/              # Checkout process
├── orders/                # Order management
├── customers/             # Customer accounts
├── user/                  # User management
├── store/                 # Store configuration
├── shipping/              # Shipping methods
├── payment/               # Payment processing
├── tax/                   # Tax calculation
├── content/               # CMS content
├── search/                # Product search
├── marketplace/           # Marketplace features
└── system/                # System configuration
```

### API Documentation Access

When running locally:
```
http://localhost:8080/swagger-ui.html
```

## Configuration Profiles

Shopizer supports multiple deployment profiles through Spring profiles:

### Available Profiles

1. **local** (default)
   - H2 embedded database
   - Local file storage
   - Development settings

2. **mysql**
   - MySQL database configuration
   - Production-ready setup

3. **docker**
   - Docker container optimized
   - Environment variable configuration

4. **cloud**
   - Cloud deployment ready
   - External configuration support

5. **aws**
   - AWS-specific configurations
   - S3 file storage

6. **gcp**
   - Google Cloud Platform configurations
   - GCP storage integration

7. **dependency**
   - External dependency management

### Configuration Files

- `application.properties` - Main Spring Boot configuration
- `shopizer-properties.properties` - Shopizer-specific settings
- `database.properties` - Database connection settings
- `shopizer-core.properties` - Core module configuration
- `email.properties` - Email server configuration

## Database Schema

The default database schema is `SALESMANAGER`. Key tables include:

- Products and catalog
- Categories
- Customers
- Orders and order items
- Merchants/stores
- Users and permissions
- Content and CMS
- Shipping and tax configurations

## Running the Application

### Prerequisites

- Java 11 or Java 17
- Maven 3.x (or use included Maven wrapper)

### Build and Run

#### Option 1: Using Maven Wrapper (Recommended)

```bash
# Build the entire project
./mvnw clean install

# Run the application
cd sm-shop
./mvnw spring-boot:run
```

#### Option 2: Using Docker

```bash
# Run backend API
docker run -p 8080:8080 shopizerecomm/shopizer:latest

# Run admin interface (requires backend running)
docker run \
  -e "APP_BASE_URL=http://localhost:8080/api" \
  -p 82:80 shopizerecomm/shopizer-admin

# Run React shop sample
docker run \
  -e "APP_MERCHANT=DEFAULT" \
  -e "APP_BASE_URL=http://localhost:8080" \
  -p 80:80 shopizerecomm/shopizer-shop-reactjs
```

### Access Points

- **API Documentation:** http://localhost:8080/swagger-ui.html
- **API Base URL:** http://localhost:8080/api
- **Health Check:** http://localhost:8080/actuator/health

## Project Structure Details

### Key Java Packages

```
com.salesmanager.shop/
├── application/           # Spring Boot application and configuration
│   ├── ShopApplication.java  # Main entry point
│   └── config/            # Configuration classes
├── store/
│   ├── api/               # REST API controllers
│   │   ├── v1/            # Version 1 API
│   │   └── v2/            # Version 2 API
│   ├── facade/            # Business facade layer
│   ├── security/          # Security configuration
│   ├── controller/        # Web controllers
│   └── model/             # API models
├── mapper/                # Object mappers (DTO <-> Entity)
├── populator/             # Data populators
├── utils/                 # Utility classes
├── filter/                # Request/response filters
└── constants/             # Application constants
```

### Resources Structure

```
resources/
├── application.properties     # Main configuration
├── profiles/                  # Profile-specific configs
│   ├── local/
│   ├── mysql/
│   ├── docker/
│   ├── cloud/
│   ├── aws/
│   └── gcp/
├── bundles/                   # i18n message bundles
├── templates/                 # Email and invoice templates
├── spring/                    # Spring XML configurations
└── static/                    # Static resources
```

## Key Features Implementation

### 1. Multi-Store Support
Shopizer supports multiple stores/merchants in a single installation, each with:
- Independent product catalogs
- Separate configurations
- Custom branding
- Isolated customer bases

### 2. Internationalization (i18n)
- Multi-language support
- Localized content
- Currency support
- Regional tax and shipping rules

### 3. Security
- JWT-based authentication
- Role-based access control (RBAC)
- XSS protection filters
- CORS configuration
- Secure password handling

### 4. File Storage
- Local file system storage
- AWS S3 integration
- GCP Cloud Storage support
- Configurable storage backends

### 5. Email System
- Template-based emails (Freemarker)
- Order confirmations
- Password reset
- Customer notifications
- Marketing emails

### 6. Search
- Elasticsearch integration
- Full-text product search
- Faceted search support
- Configurable search mappings

## Development Guidelines

### Adding New API Endpoints

1. Create controller in `store.api.v1` package
2. Define request/response models in `sm-shop-model`
3. Implement business logic in `sm-core` services
4. Add mappers in `mapper` package
5. Document with Swagger annotations

### Database Changes

1. Update entity classes in `sm-core-model`
2. JPA will auto-generate schema changes (dev mode)
3. For production, use migration scripts

### Adding New Modules

1. Implement in `sm-core-modules`
2. Follow plugin architecture
3. Register in `integrationmodules.json`

## Testing

- Unit tests in each module's `src/test` directory
- Integration tests for API endpoints
- Test coverage tracking with JaCoCo

## Monitoring and Operations

### Spring Boot Actuator

Enabled endpoints:
- `/actuator/health` - Health check
- `/actuator/info` - Application info
- `/actuator/metrics` - Metrics
- All actuator endpoints exposed

### Logging

- Configurable log levels
- Separate logging for:
  - Spring Framework
  - Hibernate
  - Application code
  - HTTP clients

## Deployment Considerations

### Production Checklist

1. **Database:**
   - Use MySQL or PostgreSQL (not H2)
   - Configure connection pooling (HikariCP)
   - Set up database backups

2. **Security:**
   - Configure HTTPS
   - Set strong JWT secrets
   - Enable CORS properly
   - Configure firewall rules

3. **Performance:**
   - Enable caching (Ehcache configured)
   - Configure Elasticsearch cluster
   - Set up CDN for static assets
   - Optimize database queries

4. **Monitoring:**
   - Set up application monitoring
   - Configure log aggregation
   - Enable health checks
   - Set up alerts

5. **Scaling:**
   - Use external session storage
   - Configure load balancer
   - Use cloud storage for files
   - Scale database appropriately

## Integration Points

### Payment Gateways
- Pluggable payment module architecture
- Support for multiple payment providers
- Configured via properties

### Shipping Providers
- Canada Post integration available
- Custom shipping module support
- Rule-based shipping calculations

### Email Providers
- SMTP configuration
- Template-based email system
- Async email sending

## Community and Support

- **Documentation:** https://shopizer-ecommerce.github.io/documentation/
- **Slack:** https://shopizer.slack.com
- **Stack Overflow:** Tag `shopizer`
- **GitHub:** https://github.com/shopizer-ecommerce/shopizer
- **Docker Hub:** https://hub.docker.com/r/shopizerecomm/shopizer

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request
5. Follow coding standards and include tests

## Related Projects

Shopizer ecosystem includes:

1. **shopizer** (this project) - Backend API
2. **shopizer-admin** - Admin web interface
3. **shopizer-shop-reactjs** - React-based storefront sample

## License

Apache License 2.0 - Free for commercial and personal use

## Summary

Shopizer is a comprehensive, production-ready headless e-commerce platform that provides:

- Complete REST API for e-commerce operations
- Multi-store and multi-language support
- Flexible architecture for customization
- Cloud-ready deployment options
- Active community and documentation
- Modern technology stack with Spring Boot

It's ideal for:
- Building custom e-commerce storefronts
- Creating mobile commerce apps
- Implementing omnichannel retail solutions
- Developing marketplace platforms
- Learning e-commerce architecture patterns

The headless architecture allows complete freedom in frontend technology choices while providing a robust, feature-complete backend for all e-commerce needs.
