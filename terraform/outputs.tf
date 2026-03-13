output "ecr_repository_url" {
  value = aws_ecr_repository.shopizer.repository_url
}

output "ecs_cluster_name" {
  value = aws_ecs_cluster.shopizer.name
}

output "ecs_service_name" {
  value = aws_ecs_service.shopizer.name
}
