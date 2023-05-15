# ADSS_Group_T

## Developers 
* Israel Waitzman 318888443
* Omer Tarshish 207524505
* Noam Gilad 206608945
* Noam Shurki 318375235

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

| Branch | Employee ID | Shift Date | Shift Time(morning/evening) |
|--------|-------------|------------|-----------------------------|
| b1     | 13          | 2023-05-16 | m                           |
| b1     | 14          | 2023-05-16 | m                           |
| b2     | 15          | 2023-05-16 | m                           |
| b1     | 13          | 2023-05-16 | m                           |
| b1     | 14          | 2023-05-16 | e                           |
| b1     | 15          | 2023-05-16 | m                           |
| b1     | 16          | 2023-05-16 | e                           |
| b1     | 17          | 2023-05-16 | m                           |
| b1     | 18          | 2023-05-16 | e                           |
| b1     | 19          | 2023-05-16 | m                           |
| b1     | 20          | 2023-05-16 | e                           |
| b2     | 16          | 2023-05-18 | e                           |
| b3     | 17          | 2023-05-19 | m                           |
| b3     | 18          | 2023-05-19 | e                           |
| b4     | 19          | 2023-05-20 | m                           |
| b4     | 20          | 2023-05-20 | e                           |

| Date                | Employee ID |
|---------------------|--------------|
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

| Employee ID | Name           | bank account  | description                 | Salary | Start Date  | Manager ID |
|-------------|----------------|---------------|-----------------------------|--------|-------------|------------|
| 11          | JohnDoe        | 123456789     | Good worker                 | 15000  | 2023-05-14  | 11         |
| 12          | JaneDoe        | 987654321     | Professional driver         | 20000  | 2023-05-14  | 12         |
| 13          | BobSmith       | 456789123     | Experienced HR professional | 25000  | 2023-05-14  | 13         |
| 14          | AliceJohnson  | 789123456     | Operations expert           | 30000  | 2023-05-14  | 14         |
| 15          | DavidLee      | 321654987     | Team player                 | 16000  | 2023-05-14  | 15         |
| 16          | MaryBrown     | 654987321     | Safe and reliable           | 21000  | 2023-05-14  | 16         |
| 17          | JoeWilson     | 789654123     | People person               | 26000  | 2023-05-14  | 17         |
| 18          | SaraJones     | 456321789     | Logistics pro               | 31000  | 2023-05-14  | 18         |
| 19          | MarkTaylor    | 789456123     | Hard worker                 | 17000  | 2023-05-14  | 19         |
| 20          | EmilyDavis    | 123789456     | Route optimization expert   | 22000  | 2023-05-14  | 20         |

