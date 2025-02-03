const DeleteAccount = ({ deleteAccount }) => {
    const handleDelete = e => {
        e.preventDefault()
        
        if (window.confirm('Are you sure you want to delete your account?')) {
            deleteAccount()
        }
    }
    
    return (
        <div>
            <h2>Delete Account</h2>
            <button onClick={handleDelete}>
                Delete Account
            </button>
        </div>
    )
}

export default DeleteAccount