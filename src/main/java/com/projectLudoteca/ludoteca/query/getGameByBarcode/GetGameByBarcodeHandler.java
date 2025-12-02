package com.projectLudoteca.ludoteca.query.getGameByBarcode;

import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class GetGameByBarcodeHandler {

    private final GameRepository gameRepository;

    public GetGameByBarcodeHandler(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GetGameByBarcodeAdminView handle(GetGameByBarcodeQuery query) {

        if (query.barcode() == null || query.barcode() <= 0) {
            throw new BusinessException("O código de barras é obrigatório e deve ser um número válido.");
        }

        Game game = gameRepository.findByBarcode(query.barcode())
                .orElseThrow(() -> new NoSuchElementException("Jogo não encontrado para o código informado."));

        return new GetGameByBarcodeAdminView(game.getId(), game.getTitle());
    }

}