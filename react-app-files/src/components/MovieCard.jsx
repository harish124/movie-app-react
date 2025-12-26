const MovieCard = ({ movie }) => {
    console.log("Movie: ");
    console.log(JSON.stringify(movie, null, 2));
    const { id, movieName, voteAvg, posterPath, releaseDate, ogLanguage } = movie;
    return (
        <div className="movie-card">
            <img src={posterPath ? posterPath : '/no-movies.png'} alt={movieName} />

            <div className="mt-4">
                <h3>{movieName}</h3>
                <div className="content">
                    <div className="rating">
                        <img src="/star.svg" alt="Star Icon" />
                        <p>{voteAvg ? voteAvg.toFixed(1) : 'N/A'} </p>
                        <span>•</span>
                        <p className="lang">{ogLanguage}</p>
                        <span>•</span>
                        <p className="year">
                            {releaseDate ? releaseDate.split('-')[0] : 'N/A'}
                        </p>

                    </div>
                </div>

            </div>
        </div>
    )
}

export default MovieCard
