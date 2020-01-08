package Service;

import Model.Token;
import Exception.TooManyTokensException;
import java.util.ArrayList;

public class TokenService {

    private final int maxAmountOfTokens = 6;

    public ArrayList<Token> generateTokens(int amount) {
        ArrayList<Token> res = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            res.add(new Token());
        }

        return res;
    }

    public ArrayList<Token> requestNewTokens(ArrayList<Token> exitingTokens) throws TooManyTokensException {
        ArrayList<Token> generatedTokens = new ArrayList<>();

        if (exitingTokens.size() < 2) {
            generatedTokens.addAll(generateTokens(maxAmountOfTokens - exitingTokens.size()));
        } else {
            throw new TooManyTokensException("You have too many tokens to get new ones.");
        }

        return generatedTokens;
    }
}
