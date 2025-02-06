const DeleteAccount = ({ deleteAccount }) => {
    const handleDelete = e => {
        e.preventDefault()
        
        if (window.confirm('Are you sure you want to delete your account?')) {
            deleteAccount()
        }
    }
    
    return (
        <div>
            <h2 className='m-5 text-2xl text-center font-mono font-bold'>Delete Account</h2>
            <div className="flex justify-center">
                <button className='w-48 m-3 p-1.5 bg-black text-white font-mono hover:bg-neutral-700 rounded-4xl' onClick={handleDelete}>
                    Delete Account
                </button>
            </div>
        </div>
    )
}

export default DeleteAccount