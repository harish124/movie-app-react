import React from "react"
const Search = ({ searchTerm, setSearchTerm }) => {
    return (
        <div className="search">
            <div>
                <img src="search.svg" />
                <input type="text"
                    placeholder="நூற்றுக்கணக்கான திரைப்படங்களை தேடுங்கள்"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
            </div>
        </div>
    )
}

export default Search
