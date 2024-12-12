import React, { useState, useEffect, useRef } from 'react'
import Navbar from '../components/Navbar'
import Pagination from '../components/Pagination';
import Popup from 'reactjs-popup';
// import 'reactjs-popup/dist/index.css';
import './CustomerPage.css';

function CustomerPage() {
  // Pagination variables for General Customer List (table)
  const [customer, setCustomer] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage, setPostsPerPage] = useState(20); // 20
  // Pagination variables for Rental History
  const [currentPageHistory, setCurrentPageHistory] = useState(1);
  const [postsPerPageHistory, setPostsPerPageHistory] = useState(5);

  const [countries, setCountries] = useState([]);

  /* Display customers for  initial display */
  useEffect(() => {
    fetch('http://localhost:5000/customers')
      .then(response => response.json())
      .then(data => setCustomer(data))
      .catch(error => console.error('Error fetching data: ', error));
    fetch('http://localhost:5000/customerCountries')
      .then(response => response.json())
      .then(data => setCountries(data))
      .catch(error => console.error('Error fetching data: ', error));
    // console.log(countries);
    console.log(customer);
  }, [])

  // Pagination Math for General Customer List (table )
  const lastPostIndex = currentPage * postsPerPage;
  const firstPostIndex = lastPostIndex - postsPerPage;
  const currentPosts = customer.slice(firstPostIndex, lastPostIndex);


  const [customerID, setCustomerID] = useState('');
  const [customer_first_name, setCustomerFirst] = useState('');
  const [customer_last_name, setCustomerLast] = useState('');
  const [fieldValidMsg, setFieldValidMsg] = useState('');
  const [rental_history, setRentalHistory] = useState([]);
  const resetHelper = useRef();
  const resetHelperForm = useRef();

  // Pagination Math for Rental history
  const lastPostIndexHistory = currentPageHistory * postsPerPageHistory;
  const firstPostIndexHistory = lastPostIndexHistory - postsPerPageHistory;
  let current_rental_history = rental_history.slice(firstPostIndexHistory, lastPostIndexHistory);

  const handleRentalHistory = (the_customer_id) => {
    console.log("Im an inside handleRentalHistory");
    setRentalHistory([]);
    console.log(the_customer_id);
    fetch(`http://localhost:5000/rentalHistory/${the_customer_id}`)
      .then(response => response.json())
      .then(data => setRentalHistory(data))
      //.then(film => console.log(film))
      .catch(error => console.error('Error fetching rental history data:', error));
    console.log("This is rental_history: ", rental_history);
    current_rental_history = [];
    setCurrentPageHistory(1);
    setPostsPerPageHistory(5);
    current_rental_history = rental_history.slice(firstPostIndex, lastPostIndex);
  }

  const handleSubmitCustomerLast = (event) => {
    event.preventDefault();
    const regex = /^[a-zA-Z]+$/;
    if (!regex.test(customer_last_name)) {
      setFieldValidMsg('Check your fields. Data field must only contain letters.');
    } else {
      setFieldValidMsg('');
      setCustomer([]);
      var local = null;
      local = customer_last_name;
      const jsonData = [local];
      setCustomerLast('');
      resetHelper.current.value = '';

      // Now fetching...
      const url = 'http://localhost:5000/customers_by_last';
      fetch(url, {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData),
      })
        .then(response => response.json())
        .then(data => setCustomer(data))
        .catch((error) => {
          console.error('Error:', error);
        });
    }
  }

  const handleSubmitCustomerFirst = (event) => {
    event.preventDefault();
    const regex = /^[a-zA-Z]+$/;
    if (!regex.test(customer_first_name)) {
      setFieldValidMsg('Check your fields. Data field must only contain letters.');
    } else {
      setFieldValidMsg('');
      setCustomer([]);
      var local = null;
      local = customer_first_name;
      const jsonData = [local];
      setCustomerFirst('');
      resetHelper.current.value = '';

      // Now fetching...
      const url = 'http://localhost:5000/customers_by_first';
      fetch(url, {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData),
      })
        .then(response => response.json())
        //.then(data => console.log(data)) // will later be changed to actually store the received data
        .then(data => setCustomer(data))//setOutputCustomer(data))
        .catch((error) => {
          console.error('Error:', error);
        });
    }
    console.log(customer);
  }

  const handleSubmitCustomerID = (event) => {
    event.preventDefault();
    const regex = /^[0-9]+$/;
    if (!regex.test(customerID)) {
      setFieldValidMsg('Check your fields. Data field must only contain numbers');
    } else {
      setFieldValidMsg('');
      setCustomer([]);//setOutputCustomer([]);
      var local = null;
      local = customerID;
      const jsonData = [local];
      setCustomerID('');
      resetHelper.current.value = '';

      // Now fetching...
      const url = 'http://localhost:5000/customers_by_id';
      fetch(url, {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData),
      })
        .then(response => response.json())
        //.then(data => console.log(data)) // will later be changed to actually store the received data
        .then(data => setCustomer(data))//setOutputCustomer(data))
        .catch((error) => {
          console.error('Error:', error);
        });
    }
  }

  const [add_first_name, setAddFirst] = useState('');
  const [add_last_name, setAddLast] = useState('');
  const [add_email, setAddEmail] = useState('');
  const [add_address, setAddAddress] = useState('');
  const [add_district, setAddDistrict] = useState('');
  const [add_city, setAddCity] = useState('');
  const [add_postal_code, setAddPostal] = useState('');
  const [add_phone, setAddPhone] = useState('');
  const [add_country, setAddCountry] = useState('');
  const [other_add_ready, setOtherAddReady] = useState('');

  const handleAddCustomer = (event) => {
    event.preventDefault();
    const regex_alpha = /^[a-zA-Z]+$/;
    const regex_alpha_space = /^[a-zA-Z\s]+$/;
    const regex_alphanumeric_space = /^[a-zA-Z0-9\s]+$/;
    const regex_email = /^[a-zA-Z0-9]+@[a-zA-Z]+\.[a-z]{2,4}$/;
    const regex_numeric = /^[0-9]{5,}$/;
    if (regex_alpha.test(add_first_name) && regex_alpha.test(add_last_name)
      && regex_email.test(add_email) && regex_alphanumeric_space.test(add_address)
      && regex_alpha.test(add_district) && regex_alpha_space.test(add_city)
      && regex_numeric.test(add_postal_code) && regex_alpha_space.test(add_country)
      && regex_numeric.test(add_phone)
    ) {
      console.log("We are allowed to fetch.");
      var first_name = add_first_name;
      var last_name = add_last_name;
      var email = add_email;
      var address = add_address;
      var district = add_district;
      var city = add_city;
      var postal_code = add_postal_code;
      var country = add_country;
      var phone = add_phone;
      const jsonData = [first_name, last_name, email, address, district, city, postal_code, country, phone];
      const url = 'http://localhost:5000/addCustomer';
      fetch(url, {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData),
      })
        .then(response => response.json())
        //.then(data => console.log(data)) // will later be changed to actually store the received data
        .then(data => {
          if (data) {
            alert(data);
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
      setAddFirst(''); setAddLast(''); setAddEmail(''); setAddAddress(''); setAddDistrict(''); setAddCity('');
      setAddPostal(''); setAddCountry(''); setAddPhone('');

    } else {
      alert("Check your fields.");
    }
    // console.log("this is the country to be changed (name): ", add_country);
    resetHelper.current.value = '';

  }

  const handleSelectAddCountry = (event) => {
    setOtherAddReady('');
    setAddCountry('');
    if (event.target.value == 'other') {
      setOtherAddReady('ready');
    } else {
      setAddCountry(event.target.value);
      console.log("This is the country id to add: ", event.target.value);
    }
    resetHelper.current.value = '';
  }

  const [update_first_name, setUpdateFirst] = useState('');
  const [update_last_name, setUpdateLast] = useState('');
  const [update_email, setUpdateEmail] = useState('');
  const [update_address, setUpdateAddress] = useState('');
  const [update_district, setUpdateDistrict] = useState('');
  const [update_city, setUpdateCity] = useState('');
  const [update_postal_code, setUpdatePostal] = useState('');
  const [update_phone, setUpdatePhone] = useState('');
  const [update_country, setUpdateCountry] = useState('');
  const [other_update_ready, setOtherUpdateReady] = useState('');


  const handleUpdateCustomer = (event, customerID) => {
    event.preventDefault();
    console.log("Customer ID to be updated accordingly: ", customerID);
    const regex_alpha = /^[a-zA-Z]+$/;
    const regex_alpha_space = /^[a-zA-Z\s]+$/;
    const regex_alphanumeric_space = /^[a-zA-Z0-9\s]+$/;
    const regex_email = /^[a-zA-Z0-9]+@[a-zA-Z]+\.[a-z]{2,4}$/;
    const regex_numeric = /^[0-9]{5,}$/;
    console.log(update_country);
    if (regex_alpha.test(update_first_name)
      && regex_alpha.test(update_last_name)
      && regex_email.test(update_email) && regex_alphanumeric_space.test(update_address)
      && regex_alpha.test(update_district) && regex_alpha_space.test(update_city)
      && regex_numeric.test(update_postal_code)
      && regex_alpha_space.test(update_country)
      && regex_numeric.test(update_phone)
    ) {
      console.log("We are allowed to fetch.");
      var customer_id = customerID;
      var first_name = update_first_name;
      var last_name = update_last_name;
      var email = update_email;
      var address = update_address;
      var district = update_district;
      var city = update_city;
      var postal_code = update_postal_code;
      var country = update_country;
      var phone = update_phone;
      // Sending customerID as the first parameter and the data to update later
      const jsonData = [customer_id, first_name, last_name, email, address, district, city, postal_code, country, phone];
      const url = 'http://localhost:5000/updateCustomer';
      fetch(url, {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData),
      })
        .then(response => response.json())
        //.then(data => console.log(data)) // will later be changed to actually store the received data
        .then(data => {
          if (data) {
            alert(data);
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
      setUpdateFirst(''); setUpdateLast(''); setUpdateEmail(''); setUpdateAddress(''); setUpdateDistrict(''); setUpdateCity('');
      setUpdatePostal(''); setUpdateCountry(''); setUpdatePhone('');
    } else {
      alert("Check your fields.");
    }
    // console.log("this is the country to be changed (name): ", add_country);
    resetHelper.current.value = '';
  }

  const handleSelectUpdateCountry = (event) => {
    setOtherUpdateReady('');
    setUpdateCountry('');
    if (event.target.value == 'other') {
      setOtherUpdateReady('ready');
    } else {
      setUpdateCountry(event.target.value);
      console.log("This is the country id to update: ", event.target.value);

    }
  }

  // Handling Customer deletions

  const handleDeleteCustomer = (event, customerID) => {
    console.log("Will delete: ", customerID);
    var customer_id = customerID;
    const jsonData = [customer_id];
    const url = 'http://localhost:5000/deleteCustomer';
    fetch(url, {
      method: "POST",
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(jsonData),
    })
      .then(response => response.json())
      //.then(data => console.log(data)) // will later be changed to actually store the received data
      .then(data => {
        if (data) {
          alert(data);
        }
      })
      .catch((error) => {
        console.error('Error:', error);
      });
  }

  /* Handle defaul update Customer */
  const handleUpdateDefault = (first_name, last_name, email, address, district,
    city, postal_code, country, phone) => {
    setUpdateFirst(first_name);
    setUpdateLast(last_name);
    setUpdateEmail(email);
    setUpdateAddress(address);
    setUpdateDistrict(district);
    setUpdateCity(city);
    setUpdatePostal(postal_code);
    setUpdateCountry(country);
    setUpdatePhone(phone);
  }

  return (
    <div className="customerPage">
      <Navbar></Navbar>

      <div className="forMargin">
        <h2>Look for a customer:</h2>

        <form onSubmit={handleSubmitCustomerID}>
          <label className="searchBar">
            Customer ID:
            <input
              type="text"
              ref={resetHelper}
              value={customerID}
              onChange={(event) => setCustomerID(event.target.value)}
            />
            <button type="submit" className="search">Search</button>
          </label>
        </form>

        <form onSubmit={handleSubmitCustomerFirst}>
          <label className="searchBar">
            First Name:
            <input
              type="text"
              ref={resetHelper}
              value={customer_first_name}
              onChange={(event) => setCustomerFirst(event.target.value)}
            />
            <button type="submit" className="search">Search</button>
          </label>
        </form>

        <form onSubmit={handleSubmitCustomerLast}>
          <label className="searchBar">
            Last Name:
            <input
              type="text"
              ref={resetHelper}
              value={customer_last_name}
              onChange={(event) => setCustomerLast(event.target.value)}
            />
            <button type="submit" className="search">Search</button>
          </label>
        </form>

        {fieldValidMsg && <p className="warning">{fieldValidMsg}</p>}

      </div>

      <Popup className="popup_window" trigger=
        {<button className="add_customer_button">ADD New Customer</button>}
        modal nested>
        {
          close => (
            <div className="modal">
              <h3>ADDING customer:</h3>
              <div className="forMargin">
                <form className="ua_form" onSubmit={handleAddCustomer}>
                  <label className="customer_bar">
                    First Name
                    <input
                      type="text"
                      ref={resetHelperForm}
                      value={add_first_name}
                      onChange={(event) => setAddFirst(event.target.value)}
                    />
                  </label>

                  <label className="customer_bar">
                    Last Name
                    <input
                      type="text"
                      ref={resetHelperForm}
                      value={add_last_name}
                      onChange={(event) => setAddLast(event.target.value)}
                    />
                  </label>

                  <label className="customer_bar">
                    Email
                    <input
                      type="text"
                      ref={resetHelperForm}
                      value={add_email}
                      onChange={(event) => setAddEmail(event.target.value)}
                    />
                  </label>

                  <label className="customer_bar">
                    Address
                    <input
                      type="text"
                      ref={resetHelperForm}
                      value={add_address}
                      onChange={(event) => setAddAddress(event.target.value)}
                    />
                  </label>

                  <label className="customer_bar">
                    District
                    <input
                      type="text"
                      ref={resetHelperForm}
                      value={add_district}
                      onChange={(event) => setAddDistrict(event.target.value)}
                    />
                  </label>

                  <label className="customer_bar">
                    City
                    <input
                      type="text"
                      ref={resetHelperForm}
                      value={add_city}
                      onChange={(event) => setAddCity(event.target.value)}
                    />
                  </label>

                  <label className="customer_bar">
                    Postal Code
                    <input
                      type="text"
                      ref={resetHelperForm}
                      value={add_postal_code}
                      onChange={(event) => setAddPostal(event.target.value)}
                    />
                  </label>

                  <label className="customer_bar">
                    Country
                    <select onChange={handleSelectAddCountry}>
                      <option value="">Select Country</option>
                      {countries && countries.map(item => (
                        <option ref={resetHelperForm} key={item.country_id} value={item.country}>{item.country}</option>
                      ))}
                      <option value="other">Other, please specify</option>
                    </select>
                  </label>

                  <label hidden={other_add_ready !== 'ready'}>
                    Other Country
                    <input
                      type="text"
                      className="other_country_input"
                      ref={resetHelperForm}
                      value={add_country}
                      onChange={(event) => setAddCountry(event.target.value)}
                      hidden={other_add_ready !== 'ready'}
                    />
                  </label>

                  <label className="customer_bar">
                    Phone
                    <input
                      type="text"
                      ref={resetHelperForm}
                      value={add_phone}
                      onChange={(event) => setAddPhone(event.target.value)}
                    />
                  </label>

                  <button type="submit" className="add_button">Add Customer</button>
                </form>
              </div>
            </div>
          )
        }
      </Popup>
      <div className="list_table">
        <table className="my_table">
          <thead>
            <tr>
              <th>ID</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email</th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {currentPosts && currentPosts.map(item => {
              return (

                <tr key={item.customer_id}>
                  <td>{item.customer_id}</td>
                  <td>{item.first_name}</td>
                  <td>{item.last_name}</td>
                  <td>{item.email}</td>
                  <td>
                    <Popup className="popup_window" trigger=
                      {<button className="edit_button">Extra info</button>}
                      modal nested>
                      {close => (
                        <div className="modal">
                          <h3>Customer ID: {item.customer_id}</h3>
                          <div className="list_table">
                            <div className="my_table">
                              <table>
                                <thead>
                                  <tr>
                                    <th>Address</th>
                                    <th>District</th>
                                    <th>Postal Code</th>
                                    <th>Phone</th>
                                    <th>City</th>
                                    <th>Country</th>
                                  </tr>
                                </thead>
                                <tbody>
                                  <tr key={item.customer_id}>
                                    <td>{item.address}</td>
                                    <td>{item.district}</td>
                                    <td>{item.postal_code}</td>
                                    <td>{item.phone}</td>
                                    <td>{item.city}</td>
                                    <td>{item.country}</td>
                                  </tr>
                                  {/* <tr>
                                    <td>{item.address}</td>
                                    <td>{item.district}</td>
                                    <td>{item.postal_code}</td>
                                    <td>{item.phone}</td>
                                    <td>{item.city}</td>
                                    <td>{item.country}</td>
                                  </tr> */}
                                </tbody>
                              </table>
                            </div>
                          </div>
                        </div>
                      )}
                    </Popup>
                  </td>
                  <td>
                    <Popup className="popup_window" trigger=
                      {<button
                        className="more_info_button"
                        onMouseDown={() => handleRentalHistory(item.customer_id)}>Rent info</button>}
                      modal nested>
                      {close => (
                        <div className="modal">
                          <div className="list_table">
                            <div className="my_table">
                              <table>
                                <thead>
                                  <tr>
                                    <th>Rental ID</th>
                                    <th>Film Title</th>
                                    <th>Rental Date</th>
                                    <th>Return Date</th>
                                    <th>Return Status</th>
                                  </tr>
                                </thead>
                                <tbody>
                                  {current_rental_history && current_rental_history.map(item => {
                                    return (
                                      <tr key={item.rental_id}>
                                        <td>{item.rental_id}</td>
                                        <td>{item.title}</td>
                                        <td>{item.rental_date}</td>
                                        <td>{item.return_date}</td>
                                        {
                                          (item.return_date == null)
                                            ? <td>Not returned</td>
                                            : <td>Returned</td>
                                        }
                                      </tr>
                                    )
                                  })}
                                </tbody>
                              </table>
                            </div>
                            <Pagination
                              currentPage={currentPageHistory}
                              totalPosts={rental_history.length}
                              postsPerPage={postsPerPageHistory}
                              setCurrentPage={setCurrentPageHistory}
                            />
                          </div>
                        </div>
                      )}
                    </Popup>
                  </td>
                  <td>
                    <Popup className="popup_window" trigger=
                      {<button className="edit_button" 
                        onMouseDown={() => handleUpdateDefault(
                          item.first_name, item.last_name, item.email, item.address, item.district,
                          item.city, item.postal_code, item.country, item.phone
                      )}>Edit</button>}
                      modal nested>
                      {close => (
                        <div className="modal">
                          <h3>Editing customer ID: {item.customer_id}</h3>
                          <div className="forMargin">
                            <form className="ua_form" onSubmit={(event) => handleUpdateCustomer(event, item.customer_id)}>
                              <div className="edit_form_fields">
                                <div className="edit_row">
                                  <label className="customer_bar">
                                    First Name
                                    <input
                                      type="text"
                                      ref={resetHelperForm}
                                      value={update_first_name}
                                      onChange={(event) => setUpdateFirst(event.target.value)}
                                    />
                                  </label>
                                </div>

                                <div className="edit_row">
                                  <label className="customer_bar">
                                    Last Name
                                    <input
                                      type="text"
                                      ref={resetHelperForm}
                                      value={update_last_name}
                                      onChange={(event) => setUpdateLast(event.target.value)}
                                    />
                                  </label>
                                </div>

                                <div className="edit_row">
                                  <label className="customer_bar">
                                    Email
                                    <input
                                      type="text"
                                      ref={resetHelperForm}
                                      value={update_email}
                                      onChange={(event) => setUpdateEmail(event.target.value)}
                                    />
                                  </label>
                                </div>

                                <div className="edit_row">
                                  <label className="customer_bar">
                                    Address
                                    <input
                                      type="text"
                                      ref={resetHelperForm}
                                      value={update_address}
                                      onChange={(event) => setUpdateAddress(event.target.value)}
                                    />
                                  </label>
                                </div>

                                <div className="edit_row">
                                  <label className="customer_bar">
                                    District
                                    <input
                                      type="text"
                                      ref={resetHelperForm}
                                      value={update_district}
                                      onChange={(event) => setUpdateDistrict(event.target.value)}
                                    />
                                  </label>
                                </div>

                                <div className="edit_row">
                                  <label className="customer_bar">
                                    City
                                    <input
                                      type="text"
                                      ref={resetHelperForm}
                                      value={update_city}
                                      onChange={(event) => setUpdateCity(event.target.value)}
                                    />
                                  </label>
                                </div>

                                <div className="edit_row">
                                  <label className="customer_bar">
                                    Postal Code
                                    <input
                                      type="text"
                                      ref={resetHelperForm}
                                      value={update_postal_code}
                                      onChange={(event) => setUpdatePostal(event.target.value)}
                                    />
                                  </label>
                                </div>

                                <div className="edit_row">
                                  <label className="customer_bar">
                                    Country
                                    <select onChange={handleSelectUpdateCountry}>
                                      <option value="">Select Country</option>
                                      {countries && countries.map(item => (
                                        <option key={item.country_id} value={item.country}>{item.country}</option>
                                      ))}
                                      <option value="other">Other, please specify</option>
                                    </select>
                                  </label>
                                </div>

                                <div className="edit_row">
                                  <label hidden={other_update_ready !== 'ready'}>
                                    Other Country:
                                    <input
                                      type="text"
                                      className="other_country_input"
                                      ref={resetHelperForm}
                                      value={update_country}
                                      onChange={(event) => setUpdateCountry(event.target.value)}
                                      hidden={other_update_ready !== 'ready'}
                                    />
                                  </label>
                                </div>

                                <div className="edit_row">
                                  <label className="customer_bar">
                                    Phone
                                    <input
                                      type="text"
                                      ref={resetHelperForm}
                                      value={update_phone}
                                      onChange={(event) => setUpdatePhone(event.target.value)}
                                    />
                                  </label>
                                </div>

                                <div className="edit_row">
                                  <button type="submit" className="add_button">Update</button>
                                </div>

                              </div>
                            </form>
                          </div>
                        </div>
                      )}
                    </Popup>
                  </td>
                  <td>
                    <Popup className="popup_window" trigger=
                      {<button className="delete_button">Delete</button>}
                      // modal
                      nested
                    >
                      {close => (
                        <div className="modal_confirmation">
                          <h5>Are you sure you want to delete customer with ID: {item.customer_id}?</h5>
                          <button className="yesNo_button" onClick={(event) => {
                              handleDeleteCustomer(event, item.customer_id);
                              close();
                          }}>
                            Yes
                          </button>
                          <button className="yesNo_button" onClick={() => close()}>
                            No
                          </button>
                        </div>
                      )}
                    </Popup>
                  </td>
                </tr>
              )
            })
            }
          </tbody>
        </table>
      </div>
      <Pagination
        currentPage={currentPage}
        totalPosts={customer.length}
        postsPerPage={postsPerPage}
        setCurrentPage={setCurrentPage}
      />
    </div>
  )
}

export default CustomerPage