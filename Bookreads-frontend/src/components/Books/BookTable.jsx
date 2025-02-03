import Book from './Book'
import { Bookshelf } from '../../util/Bookshelf'

const BookTable = ({ books, bookshelf, userId, handleEdit, deleteBook }) => {
    return (
        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Page Count</th>
                    <th>Rating</th>
                    <th>Bookshelf</th>
                    <th>Review</th>
                    <th>Date Read</th>
                </tr>
            </thead>
            <tbody>
                {books
                    .filter(book => bookshelf === Bookshelf.ALL ? book : book.bookshelf === bookshelf)
                    .map(book => <Book key={book.id} 
                                        book={book} 
                                        userId={parseInt(userId)} 
                                        handleEdit={handleEdit} 
                                        deleteBook={deleteBook} />)}
            </tbody>
        </table>
    )
}

export default BookTable