package me.dio.sacolaapi.service;

import me.dio.sacolaapi.model.Item;
import me.dio.sacolaapi.model.Sacola;
import me.dio.sacolaapi.resource.Dto.ItemDto;
import org.springframework.http.ResponseEntity;


public interface SacolaService {
    Item incluirItemNaSacola(ItemDto itemDto);
    Sacola verSacola(Long id);
    Sacola fecharSacola(Long id, int formaPagamento);

    Sacola alterarItemNaSacola(Long id, ItemDto itemDto);

    Sacola deleteSacola(Long id);
}
