package tst2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassWithMain {

    static class TravelDTO {
        private String userName;
        private String town;

        public String getUserName() {
            return userName;
        }

        public String getTown() {
            return town;
        }

        public TravelDTO(String userName, String town) {
            this.userName = userName;
            this.town = town;
        }
    }

    //метод должен возвращать Map {имя, набор городов}. Ожидаем {Вася=[Москва, Питер], Петя=[Тула]}
    //1-й вариант реализовать церез цикл
    public Map<String, Set<String>> transformListToMap(List<TravelDTO> list) {
        /*Map<String, Set<String>> res = new HashMap<>();
        for (TravelDTO travelDTO : list) {
            Set<String> strings = res.computeIfAbsent(travelDTO.userName, s -> new HashSet<>());
            strings.add(travelDTO.getTown());
        }*/


        return list.stream()
                .collect(Collectors.groupingBy(travelDTO -> travelDTO.userName, Collectors.mapping(TravelDTO::getTown, Collectors.toSet())));
        /*return res;*/

    }

    static public void main(String[] args) {
        List<TravelDTO> list = Arrays.stream(new TravelDTO[]{new TravelDTO("Вася", "Москва"),
                        new TravelDTO("Вася", "Питер"), new TravelDTO("Петя", "Тула"), new TravelDTO("Вася", "Питер")})
                .collect(Collectors.toList());


        System.out.println(new ClassWithMain().transformListToMap(list));
    }
}
