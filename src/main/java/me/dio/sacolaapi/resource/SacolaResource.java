package me.dio.sacolaapi.resource;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.dio.sacolaapi.model.Item;
import me.dio.sacolaapi.model.Sacola;
import me.dio.sacolaapi.resource.Dto.ItemDto;
import me.dio.sacolaapi.service.SacolaService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Api(value="/ifood-devweek/sacolas")
@RestController
@RequestMapping("/ifood-devweek/sacolas")
@RequiredArgsConstructor
public class SacolaResource {
    private final SacolaService sacolaService;

    @PostMapping
    public Item incluirItemNaSacola(@RequestBody ItemDto itemDto){

        return sacolaService.incluirItemNaSacola(itemDto);
    }

    @GetMapping("/{id}")
    public Sacola verSacola(@PathVariable long id){

        return sacolaService.verSacola(id);
    }

    @PatchMapping("/fecharSacola/{sacolaId}")
    public Sacola fecharSacola(@PathVariable("sacolaId") Long sacolaId,@RequestParam("formaPagamento") int formaPagamento){
        return sacolaService.fecharSacola(sacolaId,formaPagamento);
    }
    @DeleteMapping("/{id}")
    public Sacola deleteSacola(@PathVariable("id") Long id) {
        return sacolaService.deleteSacola(id);
    }

    @PutMapping("/{id}")
    public Sacola alterarItemNaSacola(@PathVariable("id") Long id, @RequestBody ItemDto itemDto) {
        return sacolaService.alterarItemNaSacola(id, itemDto);
    }

}
