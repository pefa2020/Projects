import React, { useState, useEffect } from 'react'
import './App.css';
import Navbar from './components/Navbar'

function App() {
  const [data, setData] = useState([]);
  const [film, setFilm] = useState([]);

  const [actor, setActor] = useState([]);
  const [actorFilm, setActorFilm] = useState([]);

  useEffect(() => {
    fetch('http://localhost:5000/top5')
      .then(response => response.json())
      .then(data => setData(data))
      .catch(error => console.error('Error fetching data:', error));

    fetch('http://localhost:5000/topActors')
      .then(response => response.json())
      .then(actor => setActor(actor))
      .catch(error => console.error('Error fetching data:', error));
  }, [])


  const handleFilmDetails = (the_film_id) => {
    setFilm([]);
    console.log(the_film_id);
    fetch(`http://localhost:5000/film/${the_film_id}`)
      .then(response => response.json())
      .then(film => setFilm(film))
      //.then(film => console.log(film))
      .catch(error => console.error('Error fetching film data:', error));
  }

  const handleActorDetails = (the_actor_id) => {
    setActorFilm([]);
    //console.log(the_actor_id);
    fetch(`http://localhost:5000/actor/${the_actor_id}`)
      .then(response => response.json())
      .then(actorFilm => setActorFilm(actorFilm))
      //.then(actorFilm => console.log(actorFilm))
      .catch(error => console.error('Error fetching film data:', error));
  }

  return (
    <div className="App">
      <Navbar />
      <div className="topFive">
        <h1>Top 5 Rented Movies:</h1>

        <div className="parent1">

          <div className="p1_child1">
            {data.map(item => {
              return (
                <div key={item.film_id}>
                  <button className="info_film_button" onClick={
                    () => handleFilmDetails(item.film_id)
                  }>{item.title}</button>

                </div>
              )
            })}
          </div>

          <div className="p1_child2">
            <div>
            {film && film.map(item => {
              return (
                <div key={item.film_id}>
                  <table className="tableFilm">
                    <thead>
                      <tr>
                        <th>Film Title</th>
                        <th>Description</th>
                        <th>Release Year</th>
                        <th>Rental Rate</th>
                        <th>Rating</th>
                        <th>Special Features</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>{item.title}</td>
                        <td>{item.description}</td>
                        <td>{item.release_year}</td>
                        <td>{item.rental_rate}</td>
                        <td>{item.rating}</td>
                        <td>{item.special_features}</td>
                      </tr>
                    </tbody>
                  </table>
                  <br />
                </div>
              )
            })}
            </div>
          </div>

        </div>

        <h1>Top 5 Actors</h1>

        <div className="parent2">

          <div className="p2_child1">
            {actor.map(k => {
              return (
                <div key={k.actor_id}>
                  <button className="info_actor_button" onClick={
                    () => handleActorDetails(k.actor_id)
                  }>{k.first_name} {k.last_name}</button>
                </div>
              )
            })}
          </div>

          <div className="p2_child2">
            <ol>
              Actor's Top 5 Rented Films
              {actorFilm.map(j => {
                return (
                  <div className="ordered_list">
                  <li key={j.title}>{j.title}</li>
                  </div>
                )
              })}
            </ol>
          </div>

        </div>

      </div>
    </div>
  );
}

export default App;
