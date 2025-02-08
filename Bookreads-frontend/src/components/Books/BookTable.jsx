import Book from './Book'

const BookTable = ({ books, userId, handleEdit, deleteBook }) => {
    return (
        <div>
            {books.length === 0 ?
                <div className='mt-24 text-2xl text-red-500 text-center font-mono'>
                    There are no books in this category.
                </div> :
                <table className='mt-16 min-w-full'>
                <thead>
                    <tr>
                        <th className='w-48 py-4 text-2xl font-mono'>Title</th>
                        <th className='w-48 py-4 text-2xl font-mono'>Author</th>
                        <th className='w-48 py-4 text-2xl font-mono'>Page Count</th>
                        <th className='w-48 py-4 text-2xl font-mono'>Rating</th>
                        <th className='w-48 py-4 text-2xl font-mono'>Bookshelf</th>
                        <th className='w-48 py-4 text-2xl font-mono'>Review</th>
                        <th className='w-48 py-4 text-2xl font-mono'>Date Read</th>
                    </tr>
                </thead>
                <tbody>
                    {books
                        .map(book => <Book key={book.id} 
                                            book={book} 
                                            userId={parseInt(userId)} 
                                            handleEdit={handleEdit} 
                                            deleteBook={deleteBook} />)}
                </tbody>
            </table>}
        </div>
    )
}

export default BookTable