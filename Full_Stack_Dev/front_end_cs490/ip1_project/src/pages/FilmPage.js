import React, { useState, useEffect, useRef } from 'react'
import Navbar from '../components/Navbar'
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import './FilmPage.css';

function FilmPage() {

  const [filmName, setFilmName] = useState('');
  const [actorFirstName, setActorFirstName] = useState('');
  const [actorLastName, setActorLastName] = useState('');
  const [genre, setGenre] = useState('');
  const [validationMsg, setValidationMsg] = useState('');
  const [outputFilm, setOutputFilm] = useState([]);
  const [idRent, setCustIdRent] = useState('');
  const [filmRent, setFilmRent] = useState('');
  const resetHelper = useRef();
  const resetRentHelper = useRef();

  const handleSubmitRent = (event) => {
    event.preventDefault();
    const regex = /^[0-9]+$/;
    if (regex.test(idRent)) {
      /*Done: check if output from connection is:
        a) successful message (query worked)
        b) error from server (customer_id not found on database)
        Possible methods for validating from server: server might return an empty string which may be
        caught here in React.
      */
      const jsonData = [filmRent, idRent];
      const url = 'http://localhost:5000/validateCustomer';
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
      console.log(filmRent);
      const output = `${idRent}`;
      console.log(`Submitting form with  values: ${output}`);
      setCustIdRent('');
      resetRentHelper.current.value = '';
    } else {
      alert("Customer ID must only contain numbers.");
    }
  }

  const handleSubmitGenre = (event) => {
    const regex = /^[a-zA-Z-]+$/;
    if (!regex.test(genre)) {
      event.preventDefault();
      setValidationMsg('Check your fields. Data field must only contain letters. Symbol "-" is accepted.');
    } else {
      event.preventDefault();
      setValidationMsg('');
      var local = null;
      local = genre;
      resetHelper.current.value = '';
      setGenre('');
      const jsonData = [local];
      const url = 'http://localhost:5000/genreField';
      setOutputFilm([]);
      fetch(url, {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData),
      })
        .then(response => response.json())
        //.then(data => console.log(data)) // will later be changed to actually store the received data
        .then(data => setOutputFilm(data))
        .catch((error) => {
          console.error('Error:', error);
        });
      const output = `${genre}`;
      console.log(`Submitting form with  values: ${output}`);
    }
  }

  const handleSubmitActor = (event) => {
    event.preventDefault(); // prevents from submitting form automatically
    const regex = /^[a-zA-Z]+$/;
    console.log("Inside submit actor");
    var toTest;
    console.log(toTest)
    console.log("about to make it into if-statements");
    // Test 1: makes sure there is at least 1 input.
    if (actorFirstName == '' && actorLastName == '') {
      setValidationMsg('Check your fields. Data fields must only contain letters. You may input a first name and/or last name.');
    }
    // Test 2: makes sure that if there are 2 inputs, each input is valid (passes regex test)
    else if (actorFirstName != '' && actorLastName != '' &&
      (!regex.test(actorFirstName) || !regex.test(actorLastName))) {
      setValidationMsg('Check your fields. Data fields must only contain letters.');
    }
    // Test 3: makes sure that if there is 1 input, that input is valid (passes regex test)
    else if (!regex.test(toTest = (actorFirstName == '') ? actorLastName : actorFirstName)) {
      setValidationMsg('Check your field. Data field must only contain letters.');
    }
    // We are sending: [actorFirstName, actorLastName]. There is at least 1 non-empty var being sent.
    else {
      setValidationMsg('');
      var local_first = null;
      var local_last = null;
      local_first = actorFirstName;
      local_last = actorLastName;
      resetHelper.current.value = '';
      setActorFirstName('');
      setActorLastName('');
      console.log("HERE");
      const jsonData = [local_first, local_last];
      console.log(jsonData);
      const url = 'http://localhost:5000/actorField';
      setOutputFilm([]); //  Resets film list before adding new data
      fetch(url, {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData),
      })
        .then(response => response.json())
        //.then(data => console.log(data)) // will later be changed to actually store the received data
        .then(data => setOutputFilm(data))
        .catch((error) => {
          console.error('Error:', error);
        });
      const output = `${actorFirstName} ${actorLastName}`;
      console.log(`Submitting form with  values: ${output}`);
    }
  }

  const handleSubmitFilm = (event) => {
    const regex = /^[a-zA-Z\s]+$/;
    if (!regex.test(filmName)) {
      event.preventDefault();
      setValidationMsg('Check your field. Data field must only contain letters and spaces.');
    } else {
      // Assume that DOMAIN (input) is valid so we proceeed to submit
      event.preventDefault();
      setValidationMsg('');
      var local = null;
      local = filmName;
      resetHelper.current.value = '';
      setFilmName('');
      const jsonData = [local];
      const url = 'http://localhost:5000/filmField';
      setOutputFilm([]);
      fetch(url, {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData),
      })
        .then(response => response.json())
        //.then(data => console.log(data)) // will later be changed to actually store the received data
        .then(data => setOutputFilm(data))
        .catch((error) => {
          console.error('Error:', error);
        });
      const output = `${filmName}`;
      console.log(`Submitting form with  values: ${output}`);
    }
  }

  return (

    <div className="filmPage">
      <Navbar></Navbar>
      <div className="forMargin">
        <h2>Search the film of your choice</h2>

        <form onSubmit={handleSubmitFilm}>
          <label className="searchBar">
            Film Name:
            <input
              type="text"
              ref={resetHelper}
              value={filmName}
              onChange={(event) => setFilmName(event.target.value)}
            />
            <button type="submit" className="search">Search</button>
          </label>
          {/* <button type="submit" className="search">Search</button> */}

        </form>

        <form onSubmit={handleSubmitActor}>
          <label className="searchBar">
            Actor First Name:
            <input
              type="text"
              ref={resetHelper}
              value={actorFirstName}
              onChange={(event) => setActorFirstName(event.target.value)}
            />
            <button type="submit" className="search">Search</button>
          </label>
          <br />
          <label className="searchBar">
            Actor Last Name:
            <input
              type="text"
              ref={resetHelper}
              value={actorLastName}
              onChange={(event) => setActorLastName(event.target.value)}
            />
            <button type="submit" className="search">Search</button>
          </label>
          {/* <button type="submit" className="search">Search</button> */}

        </form>

        <form onSubmit={handleSubmitGenre}>
          <label className="searchBar">
            Genre:
            <input
              type="text"
              ref={resetHelper}
              value={genre}
              onChange={(event) => setGenre(event.target.value)}
            />
            <button type="submit" className="search">Search</button>
          </label>

        </form>
        {validationMsg && <p className="warning">{validationMsg}</p>}
      </div>

      <div className="list_table">
        <table className="my_table">
          <thead>
            <tr>
              <th>Title</th>
              <th>Description</th>
              <th>Release Year</th>
              <th>Rental Rate</th>
              <th>Rating</th>
              <th>Special Features</th>
            </tr>
          </thead>
          <tbody>
            {outputFilm && outputFilm.map(item => {
              return (

                <tr key={item.film_id}>

                  <td>
                    <Popup className="popup_window" trigger=
                      {<button className="film_rent_button"> {item.title} </button>}
                      modal nested>
                      {
                        close => (
                          <div className="modal">
                            <div className="content">

                              <form onSubmit={handleSubmitRent}>
                                <label>
                                  Enter Customer ID to rent {item.title}:
                                  <input
                                    type="text"
                                    ref={resetRentHelper}
                                    value={idRent}
                                    onChange={(event) => { setCustIdRent(event.target.value); setFilmRent(item.film_id) }}
                                  />
                                </label>
                                <button className="rent_now_button" type="submit">Rent</button>
                              </form>

                            </div>
                          </div>
                        )
                      }
                    </Popup>
                  </td>
                  <td>{item.description}</td>
                  <td>{item.release_year}</td>
                  <td>{item.rental_rate}</td>
                  <td>{item.rating}</td>
                  <td>{item.special_features}</td>
                </tr>
              )
            })
            }
          </tbody>
        </table>
      </div>
    </div>
  )

}

export default FilmPage