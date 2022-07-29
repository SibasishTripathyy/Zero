# Zero
A small ecommerce website

## Backend Services
- Customer Service: It contains customer related operations. Here we can create/get customers &/or their addresses.
- Order Service: It contains everything related to making an order.
- Product Service: It contains product related operations. Here we can get products, create new products, fetch products in a paginated fashion etc.
    - It also contains addition of categories.
- Payment Service: It is mostly a dummy service as of now.
- Cart Service: This service helps in adding and fetching items from cart. Internally it calls order service to finalize order purchase.