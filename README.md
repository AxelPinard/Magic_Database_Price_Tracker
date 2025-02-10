# Magic_Database_Price_Tracker

Database application using PostgreSQL alongside Scryfall API to track prices of cards in the database.

# Setup Instructions
download and set up PostgreSQL. Find a video on YT to help out if you dont already have it

launch SQL shell
![image](https://github.com/user-attachments/assets/61a0c318-8a14-4b69-b840-48f2bd62206e)

enter through and make sure the information in brackets matches with the following pic (server through Username)
![image](https://github.com/user-attachments/assets/45e31101-9e47-4509-ae7c-ae61549cd16b)

Enter in your password for postgres (make sure you keep it in mind were gonna need it later)

Copy and paste the following SQL code into the commandline following postgres=# 
CREATE TABLE
cardinfo(entry_num int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
card_name varchar(40) NOT NULL UNIQUE,
set varchar(5) NOT NULL,
num int NOT NULL,
bought_price numeric NOT NULL,
price numeric);
![image](https://github.com/user-attachments/assets/3cf383ec-fbbe-44ca-aa09-68c351433c77)

now that we have set up the database we can download the code into IntelliJ
Once we have pulled the code, navigate to the PostgreSQL_Connect.java class
We are going to adjust the private string password variable to whatever password you used for your postgres database

![image](https://github.com/user-attachments/assets/bc5bbd7d-42f1-4462-bf86-0d543ee87d62)

Now you should be able to run the program with no issues

# How to use the program
okay so first you need to add a card to the database. If you dont have any magic cards to enter you can try using this one by pressing 1 in the terminal for Add card
Name: Reckless Endeavor
SET: AFC
NUM: 33
BOUGHT PRICE: 0.73
We now have 1 card in the database and we can verify by pressing 2 to display cards
Finally we can use option 4 to get the current price of the card
enter in the cards name (Reckless Endeavor) then press 2 and we should see that the current price has been updated.
