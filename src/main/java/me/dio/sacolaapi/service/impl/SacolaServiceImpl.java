package me.dio.sacolaapi.service.impl;

import lombok.RequiredArgsConstructor;
import me.dio.sacolaapi.SacolaApiApplication;
import me.dio.sacolaapi.enumeration.FormaPagamento;
import me.dio.sacolaapi.model.Item;
import me.dio.sacolaapi.model.Produto;
import me.dio.sacolaapi.model.Restaurante;
import me.dio.sacolaapi.model.Sacola;
import me.dio.sacolaapi.repository.ItemRepository;
import me.dio.sacolaapi.repository.ProdutoRepository;
import me.dio.sacolaapi.repository.SacolaRepository;
import me.dio.sacolaapi.resource.Dto.ItemDto;
import me.dio.sacolaapi.service.SacolaService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {
    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;

    private final ItemRepository itemRepository;

    @Override
    public Item incluirItemNaSacola(ItemDto itemDto) {
        Sacola sacola = verSacola(itemDto.getIdSacola());

        if (sacola.isFechada()) {
            throw new RuntimeException("Está sacoa está fechada.");
        }
        Item itemParaSerInserido = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Essa produto não existe!");
                        }))
                .build();

        List<Item> itensDaSacola = sacola.getItens();
        if (itensDaSacola.isEmpty()) {
            itensDaSacola.add(itemParaSerInserido);
        } else {
            Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItemParaAdicionar = itemParaSerInserido.getProduto().getRestaurante();
            if (restauranteAtual.equals(restauranteDoItemParaAdicionar)) {
                itensDaSacola.add(itemParaSerInserido);
            } else {
                throw new RuntimeException("Não é possivel adicionar produtos de restaurantes diferentes. Feche a sacola ou esvazie.");
            }
        }
        sacolaRepository.save(sacola);
        return itemParaSerInserido;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Essa sacola não existe");
                }
        );
    }
    @Override
    public Sacola fecharSacola(Long id, int numeroFormaPagamento) {
        Sacola sacola = verSacola(id);
        if (sacola.getItens().isEmpty()) {
            throw new RuntimeException("Inclua itens na sacola");
        }

        FormaPagamento formaPagamento = numeroFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);
    }
}
