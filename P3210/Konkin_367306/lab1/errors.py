class FileError(Exception):
    """
      Represents file-related errors.
    """

    def __init__(self, message) -> None:
        super().__init__("File error occured: " + message)
        self.message = "File error occured: " + message

    def __str__(self) -> str:
        return self.message


class MatrixError(Exception):
    """
      Represents matrix-related errors.
    """

    def __init__(self, message) -> None:
        super().__init__("Matrix error occured: " + message)
        self.message = "Matrix error occured: " + message

    def __str__(self) -> str:
        return self.message