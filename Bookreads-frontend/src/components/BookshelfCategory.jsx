const BookshelfCategory = ({ name, handleClick }) => {
    return (
        <div>
            <button onClick={handleClick}>
                {name}
            </button>
        </div>
    )
}

export default BookshelfCategory