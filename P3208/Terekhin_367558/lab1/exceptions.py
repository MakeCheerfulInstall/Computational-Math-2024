class DiagonalDominatingError(Exception):
    def __init__(self: Exception, message: str):
        super().__init__(message)


class ParsingError(Exception):
    def __init__(self: Exception, message: str):
        super().__init__(message)


class InterpolationError(Exception):
    def __init__(self: Exception, message: str):
        super().__init__(message)
