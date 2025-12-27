import React, { useEffect, useState } from "react";
import { useDebounce } from "react-use";
import Search from "./components/Search.jsx";
import Spinner from "./components/Spinner.jsx";
import MovieCard from "./components/MovieCard.jsx";

const API_BASE_URL = "http://localhost:8080/v1/movies-data";
const API_OPTIONS = {
    method: 'GET',
    headers: {
        accept: 'application/json',
    },
    cache: 'no-store'
};

const App = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [debouncedSearchTerm, setDebouncedSearchTerm] = useState('');
    const [errorMsg, setErrorMsg] = useState('');
    const [movies, setMovies] = useState([]);
    const [isLoading, setLoading] = useState(false);
    const [totalPages, setTotalPages] = useState(1);
    const [currentPage, setCurrentPage] = useState(1);
    const [pageSize, setPageSize] = useState(20);

    useDebounce(() => setDebouncedSearchTerm(searchTerm), 500, [searchTerm]);

    const fetchMovies = async (query = '', page = 1, pageSize = 10) => {
        setLoading(true);
        setErrorMsg('');
        try {
            const endpoint = query
                ? `${API_BASE_URL}/getMoviesContainingChars?characters=${searchTerm}&page=${page}&pageSize=${pageSize}`
                : `${API_BASE_URL}/getMovies?page=${page}&pageSize=${pageSize}`;

            const response = await fetch(endpoint, API_OPTIONS);

            if (!response.ok) {
                throw new Error("Error while fetching movies data");
            }
            const data = await response.json();

            setMovies(data.content || []); // Assuming the response has 'movies' field
            setTotalPages(data.totalPages || 1); // Assuming the response has 'totalPages'
        } catch (error) {
            console.error(`Error while fetching movies: ${error}`);
            setErrorMsg('Error while fetching movies');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchMovies(debouncedSearchTerm, currentPage, pageSize);
    }, [debouncedSearchTerm, currentPage, pageSize]);

    const handlePageChange = (newPage) => {
        if (newPage >= 1 && newPage <= totalPages) {
            setCurrentPage(newPage);
        }
    };

    return (
        <main>
            <div className="pattern" />
            <div className="wrapper">
                <header>
                    <img src="./hero.png" alt="hero Banner" />
                    <h1>
                        உங்கள் <span className="text-gradient"> திரைப்பட </span> பயணம் இனிமேல் சுலபம்
                    </h1>
                    <Search searchTerm={searchTerm} setSearchTerm={setSearchTerm} />
                </header>

                <section className="all-movies">
                    <h2 className="mt-[40px]">All Movies</h2>
                    {isLoading ? (
                        <Spinner />
                    ) : errorMsg ? (
                        <p className="text-red-500">{errorMsg}</p>
                    ) : (
                        <div>
                            <ul>
                                {movies.map((m) => (
                                    <MovieCard key={m.id} movie={m} />
                                ))}
                            </ul>
                            {/* Pagination Controls */}
                            <div className="pagination">
                                <button
                                    onClick={() => handlePageChange(currentPage - 1)}
                                    disabled={currentPage === 1}>
                                    Previous
                                </button>
                                <span>
                                    Page {currentPage} of {totalPages}
                                </span>
                                <button
                                    onClick={() => handlePageChange(currentPage + 1)}
                                    disabled={currentPage === totalPages}
                                >
                                    Next
                                </button>
                            </div>
                        </div>
                    )}
                </section>
            </div>
        </main>
    );
};

export default App;

