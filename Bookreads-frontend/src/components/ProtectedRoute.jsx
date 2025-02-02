import { Navigate } from 'react-router-dom'
import { useAuth } from './AuthContext'

const ProtectedRoute = ({ children }) => {
    const { isAuthencticated, isLoading } = useAuth()

    if (isLoading) {
        return <div>Loading...</div>
    }

    if (!isAuthencticated) {
        return <Navigate to='/login' />
    }

    return children
}

export default ProtectedRoute