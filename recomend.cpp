#include <iostream>
#include <string>
using namespace std;

// Sample data
const int MOVIE_COUNT = 5;
string movies[MOVIE_COUNT] = {"The Matrix", "Inception", "Interstellar", "The Dark Knight", "Pulp Fiction"};
string genres[MOVIE_COUNT] = {"Action Sci-Fi", "Action Sci-Fi", "Action Sci-Fi", "Action", "Crime Drama"};

const int USER_COUNT = 3;
string users[USER_COUNT] = {"Alice", "Bob", "Charlie"};
int ratings[USER_COUNT][MOVIE_COUNT] = {
    {5, 4, 0, 0, 0}, // Alice
    {0, 5, 4, 0, 0}, // Bob
    {0, 0, 0, 4, 3}  // Charlie
};

// Movies based on genre
void mv_by_genre(const string& genre) {
    cout << "Recommended movies for genre '" << genre << "':\n";
    for (int i = 0; i < MOVIE_COUNT; ++i) {
        if (genres[i] == genre) {
            cout << "- " << movies[i] << "\n";
        }
    }
}

// Movies based on user ratings
void mv_by_rating(const string& user_name) {
    int user_index = -1;
    for (int i = 0; i < USER_COUNT; ++i) {
        if (users[i] == user_name) {
            user_index = i;
            break;
        }
    }

    if (user_index == -1) {
        cout << "User not found.\n";
        return;
    }

    int movie_scores[MOVIE_COUNT] = {0};
    for (int i = 0; i < USER_COUNT; ++i) {
        if (i != user_index) {
            for (int j = 0; j < MOVIE_COUNT; ++j) {
                if (ratings[i][j] > 0 && ratings[user_index][j] == 0) {
                    movie_scores[j] += ratings[i][j];
                }
            }
        }
    }

    cout << "Recommended movies for " << users[user_index] << ":\n";
    for (int i = 0; i < MOVIE_COUNT; ++i) {
        if (movie_scores[i] > 0) {
            cout << "- " << movies[i] << " (Score: " << movie_scores[i] << ")\n";
        }
    }
}

int main() {
    int choice;
    cout << "Choose recommendation method:\n";
    cout << "1. Content-Based Filtering (by Genre)\n";
    cout << "2. Collaborative Filtering (by Ratings)\n";
    cout << "Enter choice (1 or 2): ";
    cin >> choice;

    if (choice == 1) {
        string genre;
        cin.ignore(); // Clear input buffer
        cout << "Enter genre: ";
        getline(cin, genre);
        mv_by_genre(genre);
    } else if (choice == 2) {
        string user_name;
        cin.ignore(); // Clear input buffer
        cout << "Enter user name: ";
        getline(cin, user_name);
        mv_by_rating(user_name);
    } else {
        cout << "Invalid choice.\n";
    }

    return 0;
}
