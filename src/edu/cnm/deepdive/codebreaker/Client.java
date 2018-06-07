package edu.cnm.deepdive.codebreaker;

import edu.cnm.deepdive.codebreaker.models.Game;
import edu.cnm.deepdive.codebreaker.models.Guess;
import edu.cnm.deepdive.codebreaker.services.GameService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Client {

    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://java-bootcamp.cnm.edu/rest/codebreaker/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GameService gameService = retrofit.create(GameService.class);
        Scanner scanner = new Scanner(System.in);
        Game game;
        try {
            Game gameSpecification = new Game();
            System.out.println("Enter the characters to use in this game:");
            String characters = scanner.nextLine();
            gameSpecification.setCharacters(characters);
            game = gameService.createGame(gameSpecification).execute().body();
        } catch (IOException e) {
            throw new RuntimeException("Error creating game",e);
        }
        while (true) {
            System.out.printf("Enter a Guess of %d characters:",game.getLength());
            String guessString = scanner.nextLine().trim();
            Guess guess = new Guess();
            guess.setGuess(guessString);
            try {
                Response<Guess> guessResponse = gameService.createGuess(game.getId(), guess)
                        .execute();
                Guess guessFromServer = guessResponse.body();
                System.out.printf("In Place: %d Out of Place: %d \n",
                        guessFromServer.getInPlace(),
                        guessFromServer.getOutOfPlace());
            } catch (IOException e) {
                throw new RuntimeException("Error Sending Guess",e);
            }
        }

        /*gameService.findById(UUID.fromString("237895c4-0a9e-4ebf-8162-8ae9afdad4b2")).enqueue(
                new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                Game game = response.body();
                System.out.println("Guesses: " + game.getGuesses().size() + " Characters: "
                        +game.getCharacters());
            }

            @Override
            public void onFailure(Call<Game> call, Throwable throwable) {
                throw new RuntimeException("Request Failed", throwable);
            }
        });*/
    }
}









