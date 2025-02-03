const UsernameSearch = ({ query, handleQueryChange, handleClear }) => {
    return (
        <div>
            <p>Search by username:</p>
            <input
                type='text'
                value={query}
                name='query'
                onChange={handleQueryChange}
            />
            <button onClick={handleClear}>Clear</button>
        </div>
    )
}

export default UsernameSearch