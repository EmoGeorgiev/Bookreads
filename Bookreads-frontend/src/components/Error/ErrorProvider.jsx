import { useState } from 'react'
import { ErrorContext } from './ErrorContext'


const ErrorProvider = ({ children }) => {
    const [error, setError] = useState('')

    const triggerError = message => {
        setError(message)
        setTimeout(() => {
            clearError()
        }, 3000)
    }

    const clearError = () => {
        setError('')
    }
    
    return (
        <ErrorContext.Provider value={{ error, triggerError}}>
            {children}
        </ErrorContext.Provider>
    )
}

export default ErrorProvider