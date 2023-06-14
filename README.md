# ADSS_Group_T

## Developers 
* Israel Waitzman 318888443
* Omer Tarshish 207524505
* Noam Gilad 206608945
* Noam Shurki 318375235
##
* Start the program with a parameter "CLI"/"GUI"

## START - MENU 
1. start new program - new program from scratch (without any data) 
2. load old data - data from the previous system
3. continue from last save - last changes will remain in the system
4. exit program

## LOGIN - WINDOW   
### enter your id and password

* HR manager id = 1 password = 1 - lead to ***HR Manager-Menu*** (below)
* Transporter manager id = 2 password = 2 - lead to ***Transporter Manager-Menu*** (below)

- if you choose 2 in the start menu you'll have access to Employees and driver in system :
- employees id = 11-20 passwords = 11-20 accordingly - lead to ***Employee-Menu*** (below)
- drivers id = 21-30 passwords = 21-30 accordingly - lead to ***Driver-Menu*** (below)

###  **important** - date format in the system is : **yyyy-mm-dd**

## HR Manager Menu

How to use,
Follow the prompts to enter any required information.
- add new employee - follow the prompts 
- notification - choose this to see any notification regard to any shift that doesn't meet the requirements
- add employee qualification - enter id and choose qualification from the list 
- show shift status - enter branch,date and type 
- add new driver - follow the prompts
- manage assign employee - follow the prompts and from the shift status look for the right column for any employee to choose from to assign keep follow th prompts and choose if to assign one by one or all the possible employees
- add shift requirements - choose shift (like above) and choose the requirement follow the prompt 
- manage assign driver  - choose shift (like above) and id of the driver 
- logout 

## Transporter Manager Menu 

How to use,
Follow the prompts to enter any required information.
- skip day - skip to the next day and execute the deliveries to the next day
- enter new delivery - follow the prompts 
- enter new truck - follow the prompts 
- enter new supplier - follow the prompts 
- enter new branch - follow the prompts 
- show logistic center products - show the list of products in the logistic center
- show all deliveries 
- add new product to supplier - follow the prompts
- logout 

## Employee Menu

How to use,
 - submit shift - enter id and shift by the format mentioned above
 - show submitted shift 
 - logout

## Driver Menu

How to use,
- submit shift - Follow the prompts enter id and shift by the format mentioned above
- logout



## pre-load-data

### Employees

| ID  | Name          | password | bank account  | description                 | Salary | Start Date      | Manager ID |
|-----|---------------|----------|---------------|-----------------------------|--------|-----------------|------------|
| 11  | JohnDoe       | 11       | 123456789     | Good worker                 | 15000  | LocalDate.now() | 11         |
| 12  | JaneDoe       | 12       | 987654321     | Professional driver         | 20000  | LocalDate.now() | 12         |
| 13  | BobSmith      | 13       | 456789123     | Experienced HR professional | 25000  | LocalDate.now() | 13         |
| 14  | AliceJohnson  | 14       | 789123456     | Operations expert           | 30000  | LocalDate.now() | 14         |
| 15  | DavidLee      | 15       | 321654987     | Team player                 | 16000  | LocalDate.now() | 15         |
| 16  | MaryBrown     | 16       | 654987321     | Safe and reliable           | 21000  | LocalDate.now() | 16         |
| 17  | JoeWilson     | 17       | 789654123     | People person               | 26000  | LocalDate.now() | 17         |
| 18  | SaraJones     | 18       | 456321789     | Logistics pro               | 31000  | LocalDate.now() | 18         |
| 19  | MarkTaylor    | 19       | 789456123     | Hard worker                 | 17000  | LocalDate.now() | 19         |
| 20  | EmilyDavis    | 20       | 123789456     | Route optimization expert   | 22000  | LocalDate.now() | 20         |

### Drivers

| ID  | Name       | password | bank account  | description                                      | Salary | Start Date      |
|-----|------------|----------|---------------|--------------------------------------------------|--------|-----------------|
| 21  | Driver 1   | 21       | 123456789     | Driver with license type C and no cooling        | 20000  | LocalDate.now() |
| 22  | Driver 2   | 22       | 987654321     | Driver with license type C1 and fridge cooling   | 21000  | LocalDate.now() |
| 23  | Driver 3   | 23       | 456789123     | Driver with license type E and freezer cooling   | 22000  | LocalDate.now() |
| 24  | Driver 4   | 24       | 789123456     | Driver with license type C and fridge cooling    | 23000  | LocalDate.now() | 
| 25  | Driver 5   | 25       | 321654987     | Driver with license type C1 and no cooling       | 24000  | LocalDate.now() | 
| 26  | Driver 6   | 26       | 654987321     | Driver with license type E and no cooling        | 25000  | LocalDate.now() | 
| 27  | Driver 7   | 27       | 789654123     | Driver with license type C and freezer cooling   | 26000  | LocalDate.now() | 
| 28  | Driver 8   | 28       | 456321789     | Driver with license type C1 and fridge cooling   | 27000  | LocalDate.now() |
| 29  | Driver 9   | 29       | 789456123     | Driver with license type E and fridge cooling    | 28000  | LocalDate.now() |
| 30  | Driver 10  | 30       | 123789456     | Driver with license type C and no cooling        | 29000  | LocalDate.now() | 

### Employees qualifications

| Employee ID | Qualifications           |
|-------------|--------------------------|
| 11          | shiftManager, cashier    |
| 12          | general_worker, orderly  |
| 13          | cleaning, security, storekeeper |
| 14          | storekeeper              |
| 15          | cashier                  |
| 16          | cashier, cleaning        |
| 17          | security, storekeeper, storekeeper |
| 18          | cashier                  |
| 19          | cashier, storekeeper     |
| 20          | orderly, cashier         |

### Shifts

| Branch | Employee ID | Shift Date                  | Shift Time(morning/evening) |
|--------|-------------|-----------------------------|-----------------------------|
| b1     | 13          | LocalDate.now().plusDays(1) | m                           |
| b1     | 14          | LocalDate.now().plusDays(1) | m                           |
| b2     | 15          | LocalDate.now().plusDays(1) | m                           |
| b1     | 13          | LocalDate.now().plusDays(1) | m                           |
| b1     | 14          | LocalDate.now().plusDays(1) | e                           |
| b1     | 15          | LocalDate.now().plusDays(1) | m                           |
| b1     | 16          | LocalDate.now().plusDays(1) | e                           |
| b1     | 17          | LocalDate.now().plusDays(1) | m                           |
| b1     | 18          | LocalDate.now().plusDays(1) | e                           |
| b1     | 19          | LocalDate.now().plusDays(1) | m                           |
| b1     | 20          | LocalDate.now().plusDays(1) | e                           |
| b2     | 16          | LocalDate.now().plusDays(3) | e                           |
| b3     | 17          | LocalDate.now().plusDays(4) | m                           |
| b3     | 18          | LocalDate.now().plusDays(4) | e                           |
| b4     | 19          | LocalDate.now().plusDays(5) | m                           |
| b4     | 20          | LocalDate.now().plusDays(5) | e                           |

### Drivers submited shifts

| Date                        | Employee ID  |
|-----------------------------|--------------|
| LocalDate.now().plusDays(1) | 21           |
| LocalDate.now().plusDays(2) | 21           |
| LocalDate.now().plusDays(1) | 22           |
| LocalDate.now().plusDays(1) | 23           |
| LocalDate.now().plusDays(1) | 24           |
| LocalDate.now().plusDays(1) | 25           |
| LocalDate.now().plusDays(1) | 26           |
| LocalDate.now().plusDays(1) | 27           |
| LocalDate.now().plusDays(1) | 28           |
| LocalDate.now().plusDays(1) | 29           |
| LocalDate.now().plusDays(1) | 30           |

### Deliveries

| id    | deliveryDate                | departureTime  | truckWeight | source | driverId | truckNumber | shippingArea |
|-------|-----------------------------|----------------|-------------|--------|----------|-------------|--------------|
| 0     | LocalDate.now().plusDays(2) | 12:00          | 6000        | s1     | 0        | 1002        | 1            |
| 1     | LocalDate.now().plusDays(2) | 12:00          | 4000        | s1     | 0        | 1001        | 1            |
| 2     | LocalDate.now().plusDays(3) | 12:00          | 8000        | s3     | 0        | 1003        | 1            |
| 3     | LocalDate.now().plusDays(3) | 12:00          | 6000        | s3     | 0        | 1002        | 1            |
| 4     | LocalDate.now().plusDays(3) | 12:00          | 4000        | s4     | 0        | 1001        | 1            |

### Trucks

| licenseNumber | model | weight | maxWeight | licenseType | coolingLevel |
|---------------|-------|--------|-----------|-------------|--------------|
| 1001          | t1    | 4000   | 8000      | C1          | non          |
| 1002          | t2    | 6000   | 12000     | C1          | fridge       |
| 1003          | t3    | 8000   | 160000    | C1          | freezer      |
| 1004          | t4    | 10000  | 20000     | C1          | non          |
| 1005          | t5    | 12000  | 24000     | C1          | fridge       |
| 1006          | t6    | 14000  | 28000     | C1          | freezer      |
| 1007          | t7    | 160000 | 32000     | C1          | non          |
| 1008          | t8    | 18000  | 36000     | C1          | fridge       |
| 1009          | t9    | 20000  | 40000     | C           | freezer      |
