from flask import Flask, request, jsonify
import mysql.connector
from flask_cors import CORS

# ***FLASK PART***
app = Flask(__name__)
CORS(app)

@app.route('/deleteCustomer', methods=['POST'])
def deleteCustomer():
    data = request.get_json()
    print(data)
    customer_id = data[0]
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        try:
            cursor.execute("DELETE FROM CUSTOMER WHERE CUSTOMER.customer_id = %s", (customer_id,))
            cnx.commit()
            return jsonify("Success! Customer DELETED")
        except Exception as e:
            return jsonify("Data Received BUT QUERY does not work"), 200

    except Exception as e:
        return jsonify({'message from customers_by_first': 'Something went wrong'})


@app.route('/updateCustomer', methods=['POST'])
def updateCustomer():
    data = request.get_json()
    print(data)
    customer_id = str(data[0])
    first_name = data[1]
    last_name = data[2]
    email = data[3]
    address = data[4]
    district = data[5]
    city = data[6]
    postal_code = data[7]
    country = data[8]
    phone = data[9]
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        try:
            cursor.execute("SELECT CUSTOMER.address_id FROM CUSTOMER WHERE CUSTOMER.customer_id = %s", (customer_id,))
            address_id= cursor.fetchall()[0][0]
            print("This is the address_id: ", address_id)
            # Updating Customer Table
            cursor.execute("UPDATE CUSTOMER SET first_name = %s, last_name = %s, email = %s WHERE CUSTOMER.customer_id = %s", (first_name, last_name, email, customer_id))
            cnx.commit()
            # Updating Address Table
            cursor.execute("UPDATE ADDRESS SET address = %s, district = %s, postal_code = %s, phone = %s WHERE ADDRESS.address_id = %s", (address, district, postal_code, phone, address_id))
            cnx.commit()
            # Updating City Table
            # Updating Country Table
            cursor.execute("SELECT CITY.city_id FROM CITY WHERE CITY.city = %s", (city,))
            result_city_id = cursor.fetchall()
            cursor.execute("SELECT COUNTRY.country_id FROM COUNTRY WHERE COUNTRY.country = %s", (country,))
            result_country_id = cursor.fetchall()
            if (len(result_city_id) >= 1 and len(result_country_id) >= 1):
                print("Inside comparisons. Both City & country exist in their tables")
                # Means  that both City and Country exist in their respective tables
                # Need to update Address table according to them
                # First we need to check or set a relationship between country and city
                city_id = result_city_id[0][0]
                country_id = result_country_id[0][0]
                print("Made it here")
                # If there is are foreign key relationship between tables, we proceed to next step
                # Else we fix the relationship (aka update country_id on city_id), then proceed to next step
                cursor.execute("SELECT CITY.city_id FROM CITY WHERE CITY.city_id = %s AND CITY.country_id = %s", (city_id, country_id))
                print("Made it here 2")
                result = cursor.fetchall()
                if (len(result) < 1):
                    # Means there is no relatioship between country and city. We make one.
                    cursor.execute("UPDATE CITY SET CITY.country_id = %s WHERE CITY.city_id = %s", (country_id, city_id))
                    cnx.commit()
                # Now, there is a relationship, we can safely update address table via taking city_id
                cursor.execute("UPDATE ADDRESS SET ADDRESS.city_id = %s WHERE ADDRESS.address_id = %s", (city_id, address_id))
                cnx.commit()
                return jsonify("Success! Customer data updated.")
            elif (len(result_city_id) < 1 and len(result_country_id) < 1):
                # Means both city and country are new to the system
                # must create rows for both, after that establish a relationship, then proceed to next step
                print("Inside where both city & country are new to the system")
                cursor.execute("INSERT INTO COUNTRY(country, last_update) VALUES(%s, NOW())", (country,))
                cnx.commit()
                cursor.execute("SELECT COUNTRY.country_id FROM COUNTRY WHERE COUNTRY.country = %s", (country,))
                country_id = cursor.fetchall()[0][0]
                cursor.execute("INSERT INTO CITY(city, country_id, last_update) VALUES(%s, %s, NOW())", (city, country_id))
                cnx.commit()
                cursor.execute("SELECT CITY.city_id FROM CITY WHERE CITY.city = %s AND CITY.country_id = %s", (city, country_id))
                city_id = cursor.fetchall()[0][0]
                # We now have a city_id and a country_id. Time to update the Address table with city_id
                cursor.execute("UPDATE ADDRESS SET ADDRESS.city_id = %s WHERE ADDRESS.address_id = %s", (city_id, address_id))
                cnx.commit()
                return jsonify("Success! Customer data was updated.")
            elif (len(result_city_id) < 1 or len(result_country_id) < 1):
                print("Inside where either city or country are new to the system")
                # Means that either city or country are new to the system
                # Figure out which is which and consolidate a city_id and country_id
                if (len(result_country_id) < 1):
                    # Means country is new to the system. We need to add row for both country and city
                    print("Country is new to system")
                    cursor.execute("INSERT INTO COUNTRY(country, last_update) VALUES(%s, NOW())", (country,))
                    cnx.commit()
                    cursor.execute("SELECT COUNTRY.country_id FROM COUNTRY WHERE COUNTRY.country = %s", (country,))
                    country_id = cursor.fetchall()[0][0]
                    cursor.execute("INSERT INTO CITY(city, country_id, last_update) VALUES(%s, %s, NOW())", (city, country_id))
                    cnx.commit()
                    cursor.execute("SELECT CITY.city_id FROM CITY WHERE CITY.city = %s AND CITY.country_id = %s", (city, country_id))
                    city_id = cursor.fetchall()[0][0]
                    # We now have a city_id and a country_id. Time to update the Address table with city_id
                    cursor.execute("UPDATE ADDRESS SET ADDRESS.city_id = %s WHERE ADDRESS.address_id = %s", (city_id, address_id))
                    cnx.commit()
                    return jsonify("Success! Customer data was updated.")
                else:
                    # Means  city is new to the system. We only need to add a row for city
                    print("City is new to system")
                    country_id = result_country_id[0][0]
                    cursor.execute("INSERT INTO CITY(city, country_id, last_update) VALUES(%s, %s, NOW())", (city, country_id))
                    cnx.commit()
                    print("good til here")
                    cursor.execute("SELECT CITY.city_id FROM CITY WHERE CITY.city = %s AND CITY.country_id = %s", (city, country_id))
                    city_id = cursor.fetchall()[0][0]
                    # We now hae to update Address table with city_id
                    print("good til here 2")
                    cursor.execute("UPDATE ADDRESS SET ADDRESS.city_id = %s WHERE ADDRESS.address_id = %s", (city_id, address_id))
                    cnx.commit()
                    return jsonify("Success! Customer data was updated.")
            return jsonify("Something went wrong :(")
        except Exception as e:
            return jsonify("Data Received BUT QUERY does not work"), 200

    except Exception as e:
        return jsonify("Something went wrong")


@app.route('/addCustomer', methods=['POST'])
def addCustomer():
    data = request.get_json()
    print(data)
    first_name = data[0]
    last_name = data[1]
    email = data[2]
    address = data[3]
    district = data[4]
    city = data[5]
    postal_code = data[6]
    country = data[7]
    phone = data[8]
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password', database='sakila')
        cursor = cnx.cursor()
        try:
            # Main Goal: insert a row in Customer table.
            # There are dependencies, we take care of them first. Gathering of necessary ids
            # Checking for country_id 
            cursor.execute("SELECT COUNTRY.country_id FROM COUNTRY WHERE COUNTRY.country = %s", (country,))
            result = cursor.fetchall()
            print("length of result: ", len(result))
            if (len(result) < 1):
                print("gonna have to insert this country")
                cursor.execute("INSERT INTO COUNTRY(country, last_update) VALUES(%s, NOW())", (country,))
                cnx.commit()
                cursor.execute("SELECT COUNTRY.country_id FROM COUNTRY WHERE COUNTRY.country = %s", (country,))
                country_id = cursor.fetchall()[0][0]
                print("Newly inserted Country ID: ", country_id)
            else:
                country_id = result[0][0]
                print("country exists, id is: ", country_id)
            # Checking for city_id
            cursor.execute("SELECT CITY.city_id FROM CITY WHERE CITY.city = %s", (city,))
            result = cursor.fetchall()
            if (len(result) < 1):
                cursor.execute("INSERT INTO CITY(city, country_id, last_update) VALUES(%s, %s, NOW())", (city, country_id))
                cnx.commit()
                cursor.execute("SELECT CITY.city_id FROM CITY WHERE CITY.city = %s", (city,))
                city_id = cursor.fetchall()[0][0]
                print("Newly added city id: ", city_id)
            else:
                city_id = result[0][0]
                print("city exists, id is: ", city_id)
            # Checking for address_id
            cursor.execute("SELECT ADDRESS.address_id FROM ADDRESS WHERE ADDRESS.address = %s AND ADDRESS.district = %s AND ADDRESS.city_id = %s AND ADDRESS.postal_code = %s AND ADDRESS.phone = %s", (address, district, city_id, postal_code, phone))
            result = cursor.fetchall()
            print("result is: ", result)
            if (len(result) < 1):
                # Selecting random location (say i picked from row 1) to put to all new addresses, since location is a geometry object on the sql database and is not required by the user input
                cursor.execute("SELECT ADDRESS.location FROM ADDRESS WHERE ADDRESS.address_id = 1")
                addr = cursor.fetchall()
                my_addr = addr[0][0]
                cursor.execute("INSERT INTO ADDRESS(address, address2, district, city_id, postal_code, phone, location, last_update) VALUES(%s, NULL, %s, %s, %s, %s, %s, NOW())", (address, district, city_id, postal_code, phone, my_addr))
                cnx.commit()
                cursor.execute("SELECT ADDRESS.address_id FROM ADDRESS WHERE ADDRESS.address = %s AND ADDRESS.district = %s AND ADDRESS.city_id = %s AND ADDRESS.postal_code = %s AND ADDRESS.phone = %s", (address, district, city_id, postal_code, phone))
                address_id = cursor.fetchall()[0][0]
                print("Newly added address id: ", address_id)
            else:
                address_id = result[0][0]
                print("address exists, id is: ", address_id)
            # Checking for customer_id
            cursor.execute("SELECT CUSTOMER.customer_id FROM CUSTOMER WHERE CUSTOMER.first_name = %s AND CUSTOMER.last_name = %s AND CUSTOMER.email = %s AND CUSTOMER.address_id = %s", (first_name, last_name, email, address_id))
            result = cursor.fetchall()
            if (len(result) < 1):
                cursor.execute("INSERT INTO CUSTOMER(store_id, first_name, last_name, email, address_id, active, create_date, last_update) VALUES(1, %s, %s, %s, %s, 1, NOW(), NOW())", (first_name, last_name, email, address_id))
                cnx.commit()
                return jsonify("Success! Rows INSERTED")
            else:
                return jsonify("Customer with this data ALREADY exists")
        except Exception as e:
            return jsonify("Data Received BUT QUERY does not work"), 200

    except Exception as e:
        return jsonify("Something went wrong")

@app.route('/customerCountries', methods=['GET'])
def getCountries():
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        try:
            cursor.execute("SELECT * FROM COUNTRY", ())
            # cursor.execute(query)
            row_headers = [x[0] for x in cursor.description]
            result = cursor.fetchall()
            json_data = []
            for i in result:
                my_dict = {}
                for k1, k2 in zip(row_headers, i):
                    if isinstance(k2, set):
                        k2 = ', '.join(k2)
                    my_dict[k1] = k2
                json_data.append(my_dict)
            # print(json_data)
            return jsonify(json_data)
        except Exception as e:
            return jsonify({'message from customer countries': 'Data Received BUT QUERY does not work}'}), 200

    except Exception as e:
        return jsonify({'message from customer countries': 'Something went wrong'})

@app.route('/rentalHistory/<int:customer_id>', methods=['GET'])
def seeRentalHistory(customer_id):
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        try:
            cursor.execute("SELECT RENTAL.rental_id, FILM.title, RENTAL.rental_date, RENTAL.return_date FROM RENTAL LEFT JOIN INVENTORY ON INVENTORY.inventory_id = RENTAL.inventory_id JOIN FILM ON FILM.film_id = INVENTORY.film_id WHERE RENTAL.customer_id = %s GROUP BY RENTAL.rental_id ORDER BY RENTAL.rental_id;", (customer_id,))
            # cursor.execute(query)
            row_headers = [x[0] for x in cursor.description]
            result = cursor.fetchall()
            json_data = []
            for i in result:
                my_dict = {}
                for k1, k2 in zip(row_headers, i):
                    if isinstance(k2, set):
                        k2 = ', '.join(k2)
                    my_dict[k1] = k2
                json_data.append(my_dict)
            # print(json_data)
            return jsonify(json_data)
        except Exception as e:
            return jsonify({'message from rental_history': 'Data Received BUT QUERY does not work}'}), 200

    except Exception as e:
        return jsonify({'message from rental_history': 'Something went wrong'})

@app.route('/add_customer', methods=['POST'])
def insertCustomer():
    data = request.get_json()
    # parse data
    return

@app.route('/customers_by_last', methods=['POST'])
def getCustomers_By_Last():
    data = request.get_json()
    last_name = data[0].upper() + "%"
    print(last_name)
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        try:
            # cursor.execute("SELECT * FROM CUSTOMER WHERE CUSTOMER.last_name LIKE %s", (last_name,))
            cursor.execute("SELECT CUSTOMER.customer_id, CUSTOMER.first_name, CUSTOMER.last_name, CUSTOMER.email, ADDRESS.address, ADDRESS.district, ADDRESS.postal_code, ADDRESS.phone, CITY.city, COUNTRY.country FROM CUSTOMER JOIN ADDRESS ON ADDRESS.address_id = CUSTOMER.address_id JOIN CITY ON CITY.city_id = ADDRESS.city_id JOIN COUNTRY ON COUNTRY.country_id = CITY.country_id WHERE CUSTOMER.last_name LIKE %s ORDER BY CUSTOMER.customer_id", (last_name,))
            # cursor.execute(query)
            row_headers = [x[0] for x in cursor.description]
            result = cursor.fetchall()
            json_data = []
            for i in result:
                my_dict = {}
                for k1, k2 in zip(row_headers, i):
                    if isinstance(k2, set):
                        k2 = ', '.join(k2)
                    my_dict[k1] = k2
                json_data.append(my_dict)
            # print(json_data)
            return jsonify(json_data)
        except Exception as e:
            return jsonify({'message from customrers_by_first': 'Data Received BUT QUERY does not work}'}), 200

    except Exception as e:
        return jsonify({'message from customers_by_first': 'Something went wrong'})

@app.route('/customers_by_first', methods=['POST'])
def getCustomers_By_First():
    data = request.get_json()
    first_name = data[0].upper() + "%"
    print(first_name)
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        try:
            # cursor.execute("SELECT * FROM CUSTOMER WHERE CUSTOMER.first_name LIKE %s", (first_name,))
            cursor.execute("SELECT CUSTOMER.customer_id, CUSTOMER.first_name, CUSTOMER.last_name, CUSTOMER.email, ADDRESS.address, ADDRESS.district, ADDRESS.postal_code, ADDRESS.phone, CITY.city, COUNTRY.country FROM CUSTOMER JOIN ADDRESS ON ADDRESS.address_id = CUSTOMER.address_id JOIN CITY ON CITY.city_id = ADDRESS.city_id JOIN COUNTRY ON COUNTRY.country_id = CITY.country_id WHERE CUSTOMER.first_name LIKE %s ORDER BY CUSTOMER.customer_id", (first_name,))
            # cursor.execute(query)
            row_headers = [x[0] for x in cursor.description]
            result = cursor.fetchall()
            json_data = []
            for i in result:
                my_dict = {}
                for k1, k2 in zip(row_headers, i):
                    if isinstance(k2, set):
                        k2 = ', '.join(k2)
                    my_dict[k1] = k2
                json_data.append(my_dict)
            # print(json_data)
            return jsonify(json_data)
        except Exception as e:
            return jsonify({'message from customrers_by_first': 'Data Received BUT QUERY does not work}'}), 200

    except Exception as e:
        return jsonify({'message from customers_by_first': 'Something went wrong'})

@app.route('/customers_by_id', methods=['POST'])
def getCustomers_By_ID():
    data = request.get_json()
    customer_id = data[0]
    print(customer_id)
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        try:
            # cursor.execute("SELECT * FROM CUSTOMER WHERE CUSTOMER.customer_id = %s", (customer_id,))
            cursor.execute("SELECT CUSTOMER.customer_id, CUSTOMER.first_name, CUSTOMER.last_name, CUSTOMER.email, ADDRESS.address, ADDRESS.district, ADDRESS.postal_code, ADDRESS.phone, CITY.city, COUNTRY.country FROM CUSTOMER JOIN ADDRESS ON ADDRESS.address_id = CUSTOMER.address_id JOIN CITY ON CITY.city_id = ADDRESS.city_id JOIN COUNTRY ON COUNTRY.country_id = CITY.country_id WHERE CUSTOMER.customer_id = %s ORDER BY CUSTOMER.customer_id", (customer_id,))
            # cursor.execute(query)
            row_headers = [x[0] for x in cursor.description]
            result = cursor.fetchall()
            json_data = []
            for i in result:
                my_dict = {}
                for k1, k2 in zip(row_headers, i):
                    if isinstance(k2, set):
                        k2 = ', '.join(k2)
                    my_dict[k1] = k2
                json_data.append(my_dict)
            # print(json_data)
            return jsonify(json_data)
        except Exception as e:
            return jsonify({'message from customrers_by_id': 'Data Received BUT QUERY does not work}'}), 200

    except Exception as e:
        return jsonify({'message from customers_by_id': 'Something went wrong'})

@app.route('/customers', methods=['GET'])
def getCustomersDisplay():
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        try:
            # query = 'SELECT * FROM CUSTOMER'
            query = 'SELECT CUSTOMER.customer_id, CUSTOMER.first_name, CUSTOMER.last_name, CUSTOMER.email, ADDRESS.address, ADDRESS.district, ADDRESS.postal_code, ADDRESS.phone, CITY.city, COUNTRY.country FROM CUSTOMER JOIN ADDRESS ON ADDRESS.address_id = CUSTOMER.address_id JOIN CITY ON CITY.city_id = ADDRESS.city_id JOIN COUNTRY ON COUNTRY.country_id = CITY.country_id ORDER BY CUSTOMER.customer_id'
            cursor.execute(query)
            row_headers = [x[0] for x in cursor.description]
            result = cursor.fetchall()
            json_data = []
            for i in result:
                my_dict = {}
                for k1, k2 in zip(row_headers, i):
                    if isinstance(k2, set):
                        k2 = ', '.join(k2)
                    my_dict[k1] = k2
                json_data.append(my_dict)
            # print(json_data)
            return jsonify(json_data)
        except Exception as e:
            return jsonify({'message from customers': 'Data Received BUT QUERY does not work}'}), 200

    except Exception as e:
        return jsonify({'message from customers': 'Something went wrong'})

# Reasons why a film can't be rented out:
"""
    Decide to merge the 2 stores (so i don't have to worry about stores)
Simple difference:
RECEIVE: film_id
value = a(total copies of that film available in inventory) - b(total copies of that film rented out in rental)
A) keep track of inventory_id s
B) keep track of inventory_id s
if value < 1:
	we cannot rent it. A copy of the film is not available (all are rented out).
else:
	we can rent it. (using inventory id of the one that we can rent)
	Pick from pool of copies that one can rent.
	pool = all inventory ids from A that are not in B
	pick any inventory_id from pool and proceed rent (add row to rental)
    
"""
def checkIfCopyAvailable(film_id):
    # checks if a film can't be rented. Film = list of invetory ids
    cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
    cursor = cnx.cursor()
    cursor.execute("SELECT TA.inventory_id FROM (SELECT INVENTORY.inventory_id FROM INVENTORY WHERE INVENTORY.film_id = %s) AS TA WHERE NOT EXISTS (SELECT TB.inventory_id FROM (SELECT INVENTORY.inventory_id FROM INVENTORY JOIN RENTAL ON INVENTORY.inventory_id = RENTAL.inventory_id WHERE INVENTORY.film_id = %s AND RENTAL.return_date IS NULL) AS TB WHERE TA.inventory_id = TB.inventory_id);", (film_id, film_id))
    availableForRent = cursor.fetchall()
    print(availableForRent)
    return availableForRent


@app.route('/validateCustomer', methods=['POST'])
def validateCustomer():
    data = request.get_json()
    film_id = data[0]
    customer_id = int(data[1])
    print([film_id, customer_id])
    
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        cursor.execute("SELECT CUSTOMER.store_id FROM CUSTOMER WHERE CUSTOMER.customer_id = %s", (customer_id,))
        result = cursor.fetchall()
        print("result is: ", result, " with length: ", len(result))
        if (len(result) < 1):
            return jsonify("CUSTOMER_ID does NOT exist.") #jsonify({'message from validateCustomer': 'Data Received BUT CUSTOMER_ID does NOT exist}'}), 200
        store_id = result[0][0]
        available_rent = checkIfCopyAvailable(film_id)
        if (len(available_rent) == 0):
            return jsonify("The film you wanted is not available for rent right now.") # jsonify({'message': 'The film you wanted is not available for rent right now.'})
        
        # We know store_id = staff_id
        # print("About to insert inventory_id: ", available_rent[0][0], " customer_id: ", customer_id, " staff_id: ", store_id)
        cursor.execute("INSERT INTO RENTAL(rental_date, inventory_id, customer_id, return_date, staff_id, last_update) VALUES(NOW(), %s, %s, NULL, %s, NOW())", (available_rent[0][0], customer_id, store_id))
        cnx.commit()
        return  jsonify("Data received and processed. ROW INSERTED.") # 1 means success: jsonify({'message from validateCustomer': 'Data received and processed. ROW INSERTED'})
    except Exception as e:
        return jsonify("Could not establish a connection with database.") # jsonify({'message from validateCustomer': 'Could not establish a connection with database'}), 200

@app.route('/genreField', methods=['POST'])
def filmByGenre():
    data = request.get_json()
    category_name = data[0].capitalize() + "%"
    print(category_name)
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        try:
            cursor.execute("SELECT FILM.film_id, FILM.title, FILM.description, FILM.release_year, FILM.rental_rate, FILM.rating, FILM.special_features FROM FILM JOIN FILM_CATEGORY ON FILM_CATEGORY.film_id = FILM.film_id JOIN CATEGORY ON CATEGORY.category_id = FILM_CATEGORY.category_id WHERE CATEGORY.name LIKE %s", (str(category_name),))
            row_headers = [x[0] for x in cursor.description]
            result = cursor.fetchall()
            json_data = []
            for i in result:
                my_dict = {}
                for k1, k2 in zip(row_headers, i):
                    if isinstance(k2, set):
                        k2 = ', '.join(k2)
                    my_dict[k1] = k2
                json_data.append(my_dict)
            print(json_data)
            return jsonify(json_data)
        except Exception as e:
            return jsonify({'message from filmByGenre': 'Data Received BUT QUERY does not work}'}), 200
        
    except Exception as e:
        return jsonify({'message from filmByGenre': 'Something went wrong'})

@app.route('/actorField', methods=['POST'])
def filmByActor():
    data = request.get_json()
    print("this is data: ", data)
    # CONSIDERING both first name and/or last name of actor
    actor_first_name = data[0].upper() + "%"
    actor_last_name = data[1].upper() + "%"
    print(actor_first_name, actor_last_name)
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        try:
            cursor.execute("SELECT DISTINCT(FILM.film_id), FILM.title, FILM.description, FILM.release_year, FILM.rental_rate, FILM.rating, FILM.special_features FROM FILM JOIN FILM_ACTOR ON FILM_ACTOR.film_id = FILM.film_id JOIN ACTOR ON FILM_ACTOR.actor_id = ACTOR.actor_id WHERE ACTOR.first_name LIKE %s AND ACTOR.last_name LIKE %s", (str(actor_first_name),str(actor_last_name)))
            row_headers = [x[0] for x in cursor.description]
            result = cursor.fetchall()
            json_data = []
            for i in result:
                my_dict = {}
                for k1, k2 in zip(row_headers, i):
                    if isinstance(k2, set):
                        k2 = ', '.join(k2)
                    my_dict[k1] = k2
                json_data.append(my_dict)
            # print(json_data)
            return jsonify(json_data)
        except Exception as e:
            return jsonify({'message from filmByActor': 'Data Received BUT QUERY does not work}'}), 200

    except Exception as e:
        return jsonify({'message from filmByACtor': 'Something went wrong'})

@app.route('/filmField', methods=['POST'])
def filmByFilm():
    data = request.get_json()
    film_name = data[0].upper() + "%"
    print("-", film_name, "-")
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        try:
            cursor.execute("SELECT FILM.film_id, FILM.title, FILM.description, FILM.release_year, FILM.rental_rate, FILM.rating, FILM.special_features FROM FILM WHERE FILM.title LIKE %s", (str(film_name),))
            row_headers = [x[0] for x in cursor.description]
            result = cursor.fetchall()
            json_data = []
            for i in result:
                my_dict = {}
                for k1, k2 in zip(row_headers, i):
                    if isinstance(k2, set):
                        k2 = ', '.join(k2)
                    my_dict[k1] = k2
                json_data.append(my_dict)
            # print(json_data)
            return jsonify(json_data)
        except Exception as e:
            return jsonify({'message from filmByFilm': 'Data Received BUT QUERY does not work}'}), 200
        
    except Exception as e:
        return jsonify({'message from filmByFilm': 'Something went wrong'})


@app.route("/actor/<int:actor_id>", methods=['GET'])
def actorTopMovies(actor_id):
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        cursor.execute("SELECT FILM_ACTOR.actor_id, FILM.title FROM FILM_ACTOR JOIN INVENTORY ON INVENTORY.film_id = FILM_ACTOR.film_id JOIN RENTAL ON RENTAL.inventory_id = INVENTORY.inventory_id JOIN FILM ON FILM.film_id = FILM_ACTOR.film_id WHERE FILM_ACTOR.actor_id = %s GROUP BY FILM_ACTOR.actor_id, FILM_ACTOR.film_id ORDER BY COUNT(RENTAL.inventory_id) DESC LIMIT 5", (actor_id,))
        row_headers = [x[0] for x in cursor.description] # added
        data = cursor.fetchall()
        print(data)
        json_data = []
        for i in data:
            my_dict = {}
            for k1, k2 in zip(row_headers, i):
                my_dict[k1] = k2
            json_data.append(my_dict)
        
        cursor.close()
        #cnx.close()
        # jsonify(data)
        return jsonify(json_data)
    except Exception as e:
        return f"{str(e)}"


@app.route("/topActors")
def topFiveActors():
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        query = 'SELECT ACTOR.actor_id, ACTOR.first_name, ACTOR.last_name FROM ACTOR JOIN FILM_ACTOR ON ACTOR.actor_id = FILM_ACTOR.actor_id GROUP BY ACTOR.actor_id ORDER BY COUNT(FILM_ACTOR.actor_id) DESC LIMIT 5'
        cursor.execute(query)
        row_headers = [x[0] for x in cursor.description] # added
        data = cursor.fetchall()
        json_data = []
        for result in data:
            json_data.append(dict(zip(row_headers, result)))
        cursor.close()
        #cnx.close()
        # jsonify(data)
        return jsonify(json_data)
    except Exception as e:
        return f"{str(e)}"


@app.route("/film/<int:film_id>", methods=['GET'])
def getFilmDetails(film_id):
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        cursor.execute("SELECT * FROM FILM WHERE film_id = %s", (film_id,))
        row_headers = [x[0] for x in cursor.description]
        result = cursor.fetchall()
        json_data = []
        for i in result:
            my_dict = {}
            for k1, k2 in zip(row_headers, i):
                if isinstance(k2, set):
                    k2 = ', '.join(k2)
                my_dict[k1] = k2
            json_data.append(my_dict)
        # print(json_data)
        return jsonify(json_data)
    except Exception as e:
        return f"{str(e)}"

@app.route("/top5")
def topFiveFilms():
    try:
        cnx = mysql.connector.connect(host='localhost', user='root', password='password',database='sakila')
        cursor = cnx.cursor()
        query = 'SELECT FILM.film_id, FILM.title FROM FILM JOIN INVENTORY ON INVENTORY.FILM_ID = FILM.film_id JOIN RENTAL ON INVENTORY.inventory_id = RENTAL.inventory_id GROUP BY FILM.film_id ORDER BY COUNT(INVENTORY.film_id) DESC LIMIT 5'
        cursor.execute(query)
        row_headers = [x[0] for x in cursor.description] # added
        data = cursor.fetchall()
        json_data = []
        for result in data:
            json_data.append(dict(zip(row_headers, result)))
        cursor.close()
        #cnx.close()
        return jsonify(json_data)
    except Exception as e:
        return f"{str(e)}"

if __name__ == "__main__":
    app.run(debug=True, port=5000, host="localhost")