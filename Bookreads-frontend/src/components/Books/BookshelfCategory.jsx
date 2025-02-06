const clickedSettingStyle = 'w-52 m-1 p-1 bg-black text-white text-lg font-mono rounded-4xl'
const notClickedSettingStyle = 'w-52 m-1 p-1 bg-white text-black text-lg font-mono border border border-gray-500 rounded-4xl'

const BookshelfCategory = ({ name, currentBookshelf, bookshelf, handleBookshelf }) => {
    return (
        <button className={currentBookshelf === bookshelf ? clickedSettingStyle : notClickedSettingStyle}
                onClick={() => handleBookshelf(bookshelf)}>
            {name}
        </button>
    )
}

export default BookshelfCategory