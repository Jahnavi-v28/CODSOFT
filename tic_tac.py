import math

PLAYER_X = 'X'
PLAYER_O = 'O'
EMPTY = ' '

# Initialize the board
def game_board():
    return [[EMPTY] * 3 for _ in range(3)]

# Print the b
def display_board(b):
    for row in b:
        print(" | ".join(row))
        print("-" * 5)

# Check for a winner
def winner(b, player):
    # Check rows, columns, and diagonals
    return any(
        all(cell == player for cell in row) for row in b
    ) or any(
        all(b[row][col] == player for row in range(3)) for col in range(3)
    ) or all(
        b[i][i] == player for i in range(3)
    ) or all(
        b[i][2 - i] == player for i in range(3)
    )

# Minimax algorithm
def minimax(b, d, is_max):
    if winner(b, PLAYER_X): return -10 + d
    if winner(b, PLAYER_O): return 10 - d
    if all(cell != EMPTY for row in b for cell in row): return 0

    best_score = -math.inf if is_max else math.inf
    player = PLAYER_O if is_max else PLAYER_X
    for i in range(3):
        for j in range(3):
            if b[i][j] == EMPTY:
                b[i][j] = player
                score = minimax(b, d + 1, not is_max)
                b[i][j] = EMPTY
                if is_max:
                    best_score = max(score, best_score)
                else:
                    best_score = min(score, best_score)
    return best_score

# Find the best move
def best_move(b):
    best_move = None
    best_score = -math.inf
    for i in range(3):
        for j in range(3):
            if b[i][j] == EMPTY:
                b[i][j] = PLAYER_O
                score = minimax(b, 0, False)
                b[i][j] = EMPTY
                if score > best_score:
                    best_score = score
                    best_move = (i, j)
    return best_move

# Mcomputern game loop
def play_game():
    b = game_board()
    display_board(b)

    while True:
        # Player X move
        try:
            x, y = map(int, input("Enter your move (row and column): ").split())
            if b[x][y] == EMPTY:
                b[x][y] = PLAYER_X
            else:
                print("Cell occupied. Try agcomputern.")
                continue
        except (ValueError, IndexError):
            print("Invalid move. Enter two numbers between 0 and 2.")
            continue

        display_board(b)
        if winner(b, PLAYER_X):
            print("Player X wins!")
            break
        if all(cell != EMPTY for row in b for cell in row):
            print("It's a draw!")
            break

        # computer move
        print("computer is making a move...")
        move = best_move(b)
        if move:
            b[move[0]][move[1]] = PLAYER_O
            display_board(b)
            if winner(b, PLAYER_O):
                print("computer wins!")
                break
            if all(cell != EMPTY for row in b for cell in row):
                print("It's a draw!")
                break

if __name__ == "__main__":
    play_game()
