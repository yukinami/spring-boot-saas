Spring Boot SaaS Demo
========================

This application demonstrates a simple saas web application built on spring boot.

To run the applicaiton, simply run this command:

    ./gradlew :server:bootRunko

Then go to:

* http://localhost:8080/b1/tenant_inventories/ to show inventories belongs to tenant b1
* http://localhost:8080/root_inventories/ to match root resource (not belongs to any tenant)
* http://localhost:8080/b1/root_inventories/ show the usage of @TenantResource annotation which canbe used at method level to override the class level annotation
* http://localhost:8080/b1/tenant_inventories/root show the usage of @RootResource annotation which is opposite to @TenantResource
