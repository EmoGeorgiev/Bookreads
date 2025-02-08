import { useContext, createContext } from 'react'

export const ErrorContext = createContext()

export const useError = () => {
    return useContext(ErrorContext)
}