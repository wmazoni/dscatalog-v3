export type SpringPage<T> = {
    content: T[]
    last: boolean
    totalElements: number
    totalPages: number
    size: number
    firts: boolean
    numberOfElements: number
    empty: boolean
}