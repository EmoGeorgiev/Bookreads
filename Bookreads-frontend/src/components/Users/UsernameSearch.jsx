const UsernameSearch = ({ query, handleQueryChange, handleClear }) => {
    return (
        <div className='flex flex-col items-center'>
            <p className='m-2 text-2xl font-mono'>
                Search by username:
            </p>
            <div className='m-2'>
                <input
                    className='mt-3 mr-3 w-56 p-1.5 text-center font-mono focus:bg-gray-50 hover:bg-gray-100 border border-gray-500 rounded-4xl'
                    type='text'
                    value={query}
                    name='query'
                    onChange={handleQueryChange}
                />
                <button className='w-20 p-1.5 bg-black text-white font-mono hover:bg-neutral-700 rounded-4xl' 
                        onClick={handleClear}>
                    Clear
                </button>
            </div>
        </div>
    )
}

export default UsernameSearch