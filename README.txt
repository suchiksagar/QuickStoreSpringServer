/*
Author: Sucharith
Date: 07/21/2014
*/
==** READ ME **==

What is this app?
---------------------
This REST Spring app is intended to supply clients (in my case an iphone app) with
1. Product Catalog info
2. Create or verify user login
3. View store Information
4. Place an order (along with inventory modification)


Why did I create it?
---------------------
I developed it as part of the academic project to be submitted at the end of the semester for my course: Smart-phone based web development

So what is the idea behind it?
--------------------------------
I named my application QuickShop and this is what it does:

QuickShop is a web based iPhone app which would enable users to browse the catalogue,
select the products, shop & place an order from that particular store (Walmart in our case).
Now you may go to the particular store, pick your items, scan them using your app and swipe your card to finish your order.
The advantage of the app lies in providing the user with the shelf info, the availability & the price of
the product and also avoids the huge waiting time of the customer at the billing desk.

How to run it?
----------------
What you need?
1. MySQL server:
I have provided a file named: smartphonesDBSQL.sql file which has all the queries to create database and stuff it with demo data.
Just run it once and the data gets inserted.
2. Edit the hibernate.cfg.xml file with your database details (connection string, password & username)
3. Open the project in eclipse and run on a web server and you should have the back-end running.
4. Now a REST client on the browser such as Advanced Rest Client on Chrome browser and hit the following urls: [port no: might vary]

==>> http://localhost:8089/smartphones/getMeAllStores || Mode: GET || Returns: Json
==>> http://localhost:8089/smartphones/getProductCatalog?x=2116 || Mode: GET || Parameters: x with a value from the db, else returns no store info || Returns: Json
==>> http://localhost:8089/smartphones/getMeTheOrdersByCustomer?x=sac || Mode: GET || Parms: x which is username of the customer || Returns: Json
==>> http://localhost:8089/smartphones/createCustomer || Mode: POST || Req JSON: {"firstName":"exp", "lastName":"exp","userName":"exp", "password":"exp"} || Returns: JSON
==>> http://localhost:8089/smartphones/placeOrder || Mode: POST || Req JSON: {
"userName": "sac"
"storeZip": "2115"
"productMap": {"1": "4", "4": "8"}
}
|| Returns: JSON