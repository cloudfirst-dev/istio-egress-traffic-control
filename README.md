# Istio Engress Traffic Control

## Product Service
Simple microservice that calls another service (rating-service) through the egress controller

![rating-service](https://github.com/cloudfirst-dev/istio-egress-traffic-control/workflows/rating-service/badge.svg)

![product-service](https://github.com/cloudfirst-dev/istio-egress-traffic-control/workflows/product-service/badge.svg)

## Deploy sample

### Prerequisites
* existing service mesh control plane deployment
* project to deploy rating service (mimics an external service) - rating-service
* project to deploy product service (part of the service mesh) - product-service
* siege is installed on your workstation https://github.com/JoeDog/siege

### Deploy Services
1. Update the values in the helm/values.yaml file, the most important entry to update is the route_hostname which should reflect your cluster domain name for example if your routes are normally created as myapp-namespace.apps.example.com your route_hostname should be example.com.
1. Switch to the project that was created for product-service
    ```
    oc project product-service
    helm install egress-traffic helm
    ```

## Demonstrate Service Levels

### Environment Setup
To use the example urls below you have to setup the DOMAIN environment variable, this should match the route_hostname you specified in the deployment steps above.

```
export DOMAIN=example.com
```

### Bronze or no SLA Header

This sample will show making a call with no defined header which will default to the bronze level egress traffic policy which the rating service adds a 5 second latency timeout to the response to be able to demonstrate load.

We will first make only one single call at a time to show that each will successfully return in about 5 seconds.  You should see about 2 successful responses.
```
siege -t15s -c1 http://product-service.apps.${DOMAIN}/product/1
```

Now we will make 6 concurrent calls and see that it can handle roughly 2 concurrent connections at a time before queuing and waiting for those existing requests to complete which is why we see them incrementing by 5 seconds.  This is because even though the traffic policy is limiting to one concurrent request there is a little bit of leeway as documented in the istio docs [here](https://istio.io/latest/docs/tasks/traffic-management/circuit-breaking/#tripping-the-circuit-breaker)
```
siege -r1 -c6 http://product-service.apps.${DOMAIN}/product/1
```


### Silver SLA Header

This sample will show making a call with the header to select the silver traffic policy allows for concurrency to about 20 at a time.  We still have the latency injection from the rating service at this level.  As we can see by the results more than 1 or 2 requests to the rating service can go through concurrently.

```
siege -r1 -c20 http://product-service.apps.${DOMAIN}/product/1 -H "plan-type: silver"
```

### Gold SLA Header

This sample will show making a call with the header to select the gold traffic policy allows for concurrency to  2^32-1.  We remove the latency at this point in the rating service to show how something can be left wide open.

```
siege -r1 -c200 http://product-service.apps.${DOMAIN}/product/1 -H "plan-type: gold"
```