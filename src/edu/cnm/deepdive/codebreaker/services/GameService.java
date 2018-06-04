package edu.cnm.deepdive.codebreaker.services;

import edu.cnm.deepdive.codebreaker.models.Game;
import java.util.List;
import retrofit2.Call;

public interface GameService {

  Call<List<Game>> list();

}
