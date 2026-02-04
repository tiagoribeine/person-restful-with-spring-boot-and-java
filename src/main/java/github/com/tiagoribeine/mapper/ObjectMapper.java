package github.com.tiagoribeine.mapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import java.util.ArrayList;
import java.util.List;

public class ObjectMapper { //Nome da classe - Um mapeador genérico de objetos
    //O - Origin, D - Destination

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault(); //Cria uma instância ÚNICA do mapeador Dozer: Entidade <=> DTO ;

    //================== Metodo 1: conversão de UM objeto em outro
    public static <O,D> D parseObject(O origin, Class<D> destination){

        /* O e D são placeholders, são tipos genéricos que só serão conhecidos quando o metodo for utilizado
        Pode converter QUALQUER tipo O para QUALQUER tipo D
        Pede para o Dozer converter:
        'origin' = objeto de ENTRADA (ex: ProductRequestDTO) em 'destination' = classe de SAÍDA (ex: Product.class)
        */

     return mapper.map(origin, destination); //Retorna um objeto convertido
    }

    //================== Metodo 2: Converte uma LISTA de objetos
    public static <O,D> List<D> parseListObjects(List<O> origin, Class<D> destination){

        List<D> destinationObjects = new ArrayList<D>(); //Cria uma lista  para armazenar os resultados

        //Para cada objeto na lista de origem - realize a conversão e salve na lista
        for (Object o: origin){
            destinationObjects.add(mapper.map(o, destination)); //Objeto de origem para o Objeto de destino.
        }
        return destinationObjects;  // Retorna a lista completa com todos convertidos
    }
}
