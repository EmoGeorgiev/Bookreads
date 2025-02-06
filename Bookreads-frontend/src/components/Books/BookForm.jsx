import { useState } from 'react'
import { useAuth } from '../Auth/AuthContext'
import { Bookshelf } from '../../util/Bookshelf'

const BookForm = ({ book, save, handleCancel  }) => {
    const [title, setTitle] = useState(book.title)
    const [author, setAuthor] = useState(book.author)
    const [pageCount, setPageCount] = useState(book.pageCount)
    const [rating, setRating] = useState(book.rating)
    const [bookshelf, setBookshelf] = useState(book.bookshelf)
    const [review, setReview] = useState(book.review)
    const [dateRead, setDateRead] = useState(book.dateRead)
    const { user } = useAuth()

    const handleSave = e => {
        e.preventDefault()

        const newBook = {
            ...book,
            title,
            author,
            pageCount,
            rating,
            bookshelf,
            review,
            dateRead,
            'userId': user.id
        }
        save(newBook)
    }

    return (
        <div className=' flex justify-center'>
            <form onSubmit={handleSave}>
                <div className='flex'>
                    <div className='flex flex-col items-center'>
                        <label htmlFor='title' className='ml-5 font-semibold'>
                            Title:
                        </label>
                        <input
                            className='w-80 p-1.5 m-5 text-center font-mono hover:bg-gray-100 border border-gray-500 rounded-4xl'
                            type='text'
                            id='title'
                            value={title}
                            onChange={({ target }) => setTitle(target.value)}
                            placeholder={title}
                        />
                    </div>
                    <div className='flex flex-col items-center'>
                        <label htmlFor='author' className='ml-5 font-semibold'>
                            Author:
                        </label>
                        <input
                            className='w-80 p-1.5 m-5 text-center font-mono hover:bg-gray-100 border border-gray-500 rounded-4xl'
                            type='text'
                            id='author'
                            value={author}
                            onChange={({ target }) => setAuthor(target.value)}
                            placeholder={author}
                        />
                    </div>
                    
                </div>
                <div className='flex'>
                    <div className='flex flex-col items-center'>
                        <label htmlFor='pageCount' className='ml-5 font-semibold'>
                            Page Count:
                        </label>
                        <input
                            className='w-80 p-1.5 m-5 text-center font-mono hover:bg-gray-100 border border-gray-500 rounded-4xl'
                            type='number'
                            id='pageCount'
                            min={1}
                            value={pageCount}
                            onChange={({ target }) => setPageCount(target.value)}
                            placeholder={pageCount}
                        />
                    </div>
                    <div className='flex flex-col items-center'>
                        <label htmlFor='rating' className='ml-5 font-semibold'>
                            Rating:
                        </label>
                        <select className='w-80 p-1.5 m-5 text-center font-mono hover:bg-gray-100 border border-gray-500 rounded-4xl' name='rating' id='rating' value={rating || ''} onChange={({ target }) => setRating(target.value)} placeholder={rating}>
                            <option value=''></option>
                            <option value='5'>5</option>
                            <option value='4'>4</option>
                            <option value='3'>3</option>
                            <option value='2'>2</option>
                            <option value='1'>1</option>
                        </select>
                    </div>
                </div>
                <div className='flex'>
                    <div className='flex flex-col items-center'>
                        <label htmlFor='bookshelf' className='ml-5 font-semibold'>
                            Bookshelf:
                        </label>
                        <select className='w-80 p-1.5 m-5 text-center font-mono hover:bg-gray-100 border border-gray-500 rounded-4xl' name='bookshelf' id='bookshelf' value={bookshelf} onChange={({ target }) => setBookshelf(target.value)} placeholder={bookshelf}>
                            <option value={Bookshelf.READ}>{Bookshelf.READ.toLocaleLowerCase()}</option>
                            <option value={Bookshelf.CURRENTLY_READING}>{Bookshelf.CURRENTLY_READING.toLocaleLowerCase()}</option>
                            <option value={Bookshelf.WANT_TO_READ}>{Bookshelf.WANT_TO_READ.toLocaleLowerCase()}</option>
                        </select>
                    </div>
                    <div className='flex flex-col items-center'>
                        <label htmlFor='dateRead' className='ml-5 font-semibold'>
                            Date Read:
                        </label>
                        <input
                            className='w-80 p-1.5 m-5 text-center font-mono hover:bg-gray-100 border border-gray-500 rounded-4xl'
                            type='date'
                            id='dateRead'
                            value={dateRead || ''}
                            onChange={({ target }) => setDateRead(target.value)}
                            placeholder={dateRead}
                        />
                    </div>
                </div>
                <div className='flex flex-col items-center'>
                    <label htmlFor='review' className='ml-5 font-semibold'>
                        Review:
                    </label>
                    <textarea className='w-full h-40 p-1.5 m-5 text-center font-mono hover:bg-gray-100 border border-gray-500 rounded-xl' id='review' value={review || ''} onChange={({ target }) => setReview(target.value)}  placeholder={review}>

                    </textarea>
                </div>
                
                <div className='flex justify-center'>
                    <button className='w-60 p-1.5 m-5 bg-black text-white text-xl font-mono hover:bg-neutral-700 rounded-4xl'>
                        Save
                    </button>
                    <button className='w-60 p-1.5 m-5 bg-red-700 text-white text-xl font-mono hover:bg-red-900 rounded-4xl'
                            onClick={handleCancel}>
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    )
}

export default BookForm