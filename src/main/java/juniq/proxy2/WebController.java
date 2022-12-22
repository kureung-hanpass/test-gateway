package juniq.proxy2;

import juniq.proxy2.ApiPathRouteLocatorImpl.ApiRoute;
import juniq.proxy2.ApiPathRouteLocatorImpl.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class WebController {
    private final RouteLocator routeLocator;
    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/routes")
    String routes() {
        new Repository().add();
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        routeLocator.getRoutes()
            .subscribe(route -> System.out.println(route.getId()+" "+route.getUri()+" "+route.getPredicate()));
        return routeLocator.getRoutes().toString();
    }

    @GetMapping("/routes2")
    String routes2() {
        routeLocator.getRoutes()
            .subscribe(route -> System.out.println(route.getId()+" "+route.getUri()+" "+route.getPredicate()));
        return routeLocator.getRoutes().toString();
    }
}
