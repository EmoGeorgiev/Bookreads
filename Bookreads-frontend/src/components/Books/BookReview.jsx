const BookReview = ({ book, handleViewReview }) => {
    return (
        <div>
            <h1 className='m-10 text-center text-4xl font-semibold'>Review of &quot;{book.title}&quot; by {book.author}</h1>
            <div className='flex justify-center'>
                <div className='w-5xl text-2xl font-mono text-center'>
                    {book.review}
                </div>
            </div>
            <button className='w-24 m-5 p-1.5 mx-auto block text-white bg-red-700 hover:bg-red-900 rounded-4xl'
                    onClick={() => handleViewReview(null)}>
                Exit Review
            </button>
        </div>
    )
}

export default BookReview