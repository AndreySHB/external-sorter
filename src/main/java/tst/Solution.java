package tst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {


    /*
    Необходимо подсчитать количество подписок заданного типа среди всех переданных клиентов.

    - Результат не должен содержать ключей со значением null;
    - В случае невозможности подсчета результата метод должен возвращать пустую коллекцию.

    Пример.
    Аргументы на вход:
    clients = [subscriptionIds={"id-1"}, subscriptionIds={"id-2", "id-3"}, subscriptionIds={"id-1", "id-2", "id-3"}
    subscriptions = [{subscriptionId= "id-1", type=ANNUAL}, {subscriptionId="id-2", type=QUARTERLY}, {subscriptionId="id-3", type=MONTHLY}]

    Результат:
    { ANNUAL -> 2, QUARTERLY -> 2, MONTHLY -> 2 }
     */
    public static Map<SubscriptionType, Long> countClientSubscriptions(List<Client> clients,
                                                                       List<Subscription> subscriptions) {
        HashMap<SubscriptionType, Long> map = new HashMap<>();
        subscriptions.forEach(subscription -> map.put(subscription.getType(), 0L));
        for (Client client : clients) {
            for (String subscriptionId : client.getSubscriptionIds()) {
                for (Subscription subscription : subscriptions) {
                    if (subscription.getSubscriptionId().equals(subscriptionId)) {
                        map.computeIfPresent(subscription.getType(), (subscriptionType, aLong) -> aLong + 1);
                        break;
                    }
                }
            }
        }
        return map;
    }

    public static void main(String[] args) {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(List.of("1")));
        clients.add(new Client(List.of("2", "3")));
        clients.add(new Client(List.of("1", "2", "3")));
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription("1", SubscriptionType.ANNUAL));
        subscriptions.add(new Subscription("2", SubscriptionType.QUARTERLY));
        subscriptions.add(new Subscription("3", SubscriptionType.MONTHLY));
        Map<SubscriptionType, Long> subscriptionTypeLongMap = countClientSubscriptions(clients, subscriptions);
        System.out.println(subscriptionTypeLongMap);
    }

    public enum SubscriptionType {
        ANNUAL, QUARTERLY, MONTHLY
    }

    public static class Client {

        private final List<String> subscriptionIds;

        public Client(List<String> subscriptionIds) {
            this.subscriptionIds = subscriptionIds;
        }

        public List<String> getSubscriptionIds() {
            return subscriptionIds;
        }

    }

    public static class Subscription {
        private final String subscriptionId;
        private final SubscriptionType type;

        public Subscription(String subscriptionId, SubscriptionType type) {
            this.subscriptionId = subscriptionId;
            this.type = type;
        }

        public String getSubscriptionId() {
            return subscriptionId;
        }

        public SubscriptionType getType() {
            return type;
        }
    }
}
