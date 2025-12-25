const MovieCard = ({ movie: { _id, movieName, voteAvg, posterPath, releaseDate, ogLanguage } }) => {
    return (
        <div className="movie-card">
            <img src={posterPath ? posterPath : '/no-movies.png'} alt={movieName} />
        </div>
    )
}

export default MovieCard
