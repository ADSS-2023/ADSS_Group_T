# ADSS_Group_T

## Developers 
* Omer Guz 209438718
* Liran Azran 315018937 
* Ido Nagler 318266939 
* Yagil Brill 209382902 
##
* Start the program with a parameter "CLI"/"GUI" and one of the users : 

java -jar adss2023_v03.jar GUI StoreManager     
java -jar adss2023_v03.jar GUI Stock
java -jar adss2023_v03.jar GUI Supplier

java -jar adss2023_v03.jar CLI StoreManager     
java -jar adss2023_v03.jar CLI Stock
java -jar adss2023_v03.jar CLI Supplier


## START - MENU 
1. Load data - Load the given data from the database. If you want to load data, perform "3". Afterward, close the system and press "1" again.
2. Empty system - Delete the data from the database.
3. Set up data system - Enter data into the system, including the database.

In case of enter to a Manager Window : 

1. Enter suppliers system - Access the suppliers system.
2. Enter inventory system - Access the inventory system.
3. Skip day - Update the system to the next day.
4. Exit - Exit the system.

## Super Manager Menu at the GUI will let you choose which window you would like to go to
1. Supplier manu.
2. Stock manu.
3. Manager reports manu.

### Supplier mananger manu 

How to use :
1. Add supplier - Add a new supplier.
2 .Delete supplier - Remove a supplier.
3. Edit supplier details - Modify supplier information.
4. Add product to supplier - Add a product to a supplier.
5. Delete product of supplier - Remove a product from a supplier.
6. Edit product of supplier - Modify a product of a supplier.
7. Add discount to product - Apply a discount to a product.
8. Delete discount of product - Remove a discount from a product.
9. Edit discount of product - Modify a discount for a product.
10. Add supplier's general discounts - Add general discounts for a supplier.
11. Delete supplier's general discounts - Remove general discounts of a supplier.
12. Edit supplier's general discounts - Modify general discounts of a supplier.
13. Show all orders - View all orders.
14. Show all suppliers - Display all suppliers.
15. Show all products supplied by a certain supplier - View all products supplied by a specific supplier.
16. Show all discounts of a certain product's supplier - Display all discounts for a product from a specific supplier.
17. Show all general discounts of a certain supplier - View all general discounts of a specific supplier.
18. Go back to the main menu - Return to the main menu.

## Stock Manager Menu 

How to use :
1. See categories - View categories and products.
2. Produce inventory report - Generate an inventory report.
3. Set discount - Set a discount.
4. Report damaged item - Report a damaged product.
5. Set minimal amount for a specific item - Set a minimum quantity for a specific product.
6. Produce damaged items report - Generate a report of damaged items.
7. Add new item - Add a new product to the system.
8. Receive a new order (receive a new supply of an existing item) - Receive an order that has arrived.
9. Produce shortage report - Generate a report of shortages.
10. Add new category - Add a new category.
11. Move item to store - Move a product to the store and update its location.
12. Orders menu - Orders menu.
13. Back to the start menu - Return to the main menu.


## Employee Menu

Orders menu :

1. Edit regular order - Edit a regular order.
2. Create regular order - Create a regular order.
3. Create special order - Create an urgent order.
4. Place waiting items - Assign products that are waiting after receiving an order.
5. Show all orders for the next week - View all orders for the upcoming week.
6. Go back to the inventory menu - Return to the inventory menu.



## pre-load-data 

### Inventory categories

| index_item | Name         | father_category_index |
|------------|--------------|-----------------------|
| 0          | Milk-product |                       |
| 0.0        | Cheese       | 0                     |
| 0.1        | Bottle milk  | 0                     |
| 0.2        | Chocolate    | 0                     |
| 1          | Meat-product |                       |
| 1.0        | Beef         | 1                     |
| 1.1        | Chicken      | 1                     |

### Inventory item

| itemID | Name          | minAmount | ManufacturerName | OriginalPrice | CategoriesIndex |
|--------|---------------|-----------|------------------|---------------|-----------------|
| 0      | 3% milk       | 5         | IDO LTD          | 3.5           | .0.1            |
| 1      | 1.5% milk     | 2         | IDO LTD          | 3.5           | .0.1            |
| 2      | Yellow cheese | 5         | Emeck            | 10.2          | .0.0            |
| 4      | Click         | 5         | Elite            | 15.0          | .0.2            |
| 5      | Beef Sausage  | 15        | Zogloveck        | 10.05         | .1.0            |

### inventory item per order 

| itemId | OrderId | validity    | location         | costPrice | amountStore | amountWareHouse | arrivedDate |
|--------|---------|-------------|------------------|-----------|-------------|-----------------|-------------|
| 0      | 155     | 2023-05-20  | ile 5 shelf 10   | 2.15      | 10          | 10              | 2023-09-17  |
| 1      | 120     | 2023-05-23  | ile 5 shelf 11   | 2.55      | 5           | 5               | 2023-09-17  |
| 2      | 20      | 2023-10-25  | ile 2 shelf 3    | 5.3       | 3           | 3               | 2023-09-17  |
| 4      | 155     | 2023-05-20  | ile 5 shelf 10   | 2.15      | 10          | 10              | 2023-09-17  |
| 5      | 345     | 2023-10-20  | ile 6 shelf 2    | 12.25     | 8           | 7               | 2023-09-17  |


### inventory waiting items 

| productName   | manufacturer | quantity | expiryDate  | costPrice | orderId |
|---------------|--------------|----------|-------------|-----------|---------|
| 3% milk       | IDO LTD      | 40       | 2023-05-10  | 1.2       | 12      |
| Beef Sausage  | Zogloveck    | 16       | 2023-10-01  | 10.05     | 1005    |



### supplier discount 

| supplierNum | amount | discount | isPercentage | isTotalAmount | isSupplierDiscount | productNum |
|-------------|--------|----------|--------------|---------------|--------------------|------------|
| 1           | 100    | 5        | true         | false         | false              | 11         |
| 1           | 200    | 100      | false        | false         | false              | 12         |
| 1           | 50     | 10       | true         | false         | false              | 13         |
| 2           | 100    | 50       | false        | false         | false              | 21         |
| 2           | 20     | 5        | true         | false         | false              | 22         |
| 2           | 60     | 50       | false        | false         | false              | 23         |
| 2           | 50     | 10       | true         | false         | false              | 24         |
| 1           | 50     | 10       | true         | true          | true               | -1         |
| 2           | 500    | 50       | false        | false         | true               | -1         |
| 1           | 300    | 40       | false        | false         | true               | -1         |


### suppliers 

| supplierNum | supplierName | address                 | bankAccountNum | selfDelivery | daysToDeliver | paymentTerms   |
|-------------|--------------|-------------------------|----------------|--------------|---------------|----------------|
| 1           | Sapak1       | Shoham 43, Tel Aviv     | 1199922        | true         | -1            | SHOTEF_PLUS_30 |
| 2           | Sapak2       | Golani 2, Ashkelon      | 947182         | true         | -1            | SHOTEF_PLUS_30 |


### supplier contact : 

| supplierNum | contactName | contactNumber |
|-------------|-------------|---------------|
| 1           | yossi       | 052284621     |
| 2           | menash      | 18726312      |

### Supplier products :

| supplierNum | productNum | name           | manufacturer | price | maxAmount | expiryDate  |
|-------------|------------|----------------|--------------|-------|-----------|-------------|
| 1           | 11         | Bamba          | Osem         | 6     | 1000      | 2023-10-17  |
| 1           | 12         | Click          | Elite        | 8     | 1000      | 2023-10-01  |
| 1           | 13         | yellow cheese  | Emeck        | 6     | 1000      | 2023-10-17  |
| 1           | 14         | 1.5% milk      | IDO LTD      | 6     | 1000      | 2023-10-17  |
| 2           | 21         | Ketchup        | Heinz        | 10    | 1000      | 2023-10-17  |
| 2           | 22         | Jasmin Rice    | Sogat        | 9     | 1000      | 2024-09-17  |
| 2           | 23         | Bamba          | Osem         | 6     | 1000      | 2023-10-17  |
| 2           | 24         | 1.5% milk      | IDO LTD      | 8     | 1000      | 2023-10-17  |


### Users : 

| id | occupation |
|----|------------|
| 1  | manager    |
| 2  | stock      |
| 3  | supplier   |
