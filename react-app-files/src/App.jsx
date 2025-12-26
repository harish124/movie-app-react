import React from "react"
import { useDebounce } from "react-use";
import { useEffect, useState } from "react";
import Search from "./components/Search.jsx"
import Spinner from "./components/Spinner.jsx";
import MovieCard from "./components/MovieCard.jsx";

const API_BASE_URL = "http://localhost:8080/v1/movies-data";
const API_OPTIONS = {
    method: 'GET',
    headers: {
        accept: 'application/json',
    },
    cache: 'no-store'
}
const App = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [debouncedSearchTerm, setDevouncedSearchTerm] = useState('');
    const [errorMsg, setErrorMsg] = useState('');
    const [movies, setMovies] = useState([]);
    const [isLoading, setLoading] = useState(false);

    useDebounce(() => setDevouncedSearchTerm(searchTerm), 500, [searchTerm]);

    const fetchMovies = async (query = '') => {
        setLoading(true);
        setErrorMsg('');
        try {
            const endpoint = query ? `${API_BASE_URL}/getMoviesContainingChars?characters=${searchTerm}`
                : `${API_BASE_URL}/getMovies`;
            const response = await fetch(endpoint, API_OPTIONS);

            if (!response.ok) {
                throw new Error("Error while fetching movies data");
            }
            const data = await response.json();
            //console.log(JSON.stringify(data));
            setMovies(data || []);
        }
        catch (error) {
            console.error(`Error while fetching movies: ${error}`);
            setErrorMsg('Error while fetching movies');
        }
        finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        fetchMovies(debouncedSearchTerm)
    }, [debouncedSearchTerm])

    return (
        <main>
            <div className="pattern" />
            <div className="wrapper">
                <header>
                    <img src="./hero.png" alt="hero Banner" />
                    <h1> உங்கள் <span className="text-gradient"> திரைப்பட </span>
                        பயணம் இனிமேல் சுலபம்
                    </h1>
                    <Search searchTerm={searchTerm} setSearchTerm={setSearchTerm} />
                </header>


                <section className="all-movies">
                    <h2 className="mt-[40px]">All Movies</h2>
                    {
                        isLoading ? <Spinner />
                            : errorMsg ? (<p className="text-red-500">{errorMsg}</p>)
                                : (
                                    <ul>
                                        {
                                            movies.map((m) =>
                                                <MovieCard key={m.id} movie={m} />
                                            )
                                        }
                                    </ul>
                                )
                    }

                </section>
            </div>
        </main>
    )
}

export default App
