# pivotal-cloud-cache-lookaside-demo
The simplest PCC Demo app.

## High Level Architecture
![](https://github.com/tkaburagi/pivotal-cloud-cache-lookaside-demo/blob/master/0.png)

## Screenshots
![](https://github.com/tkaburagi/pivotal-cloud-cache-lookaside-demo/blob/master/1.png)

![](https://github.com/tkaburagi/pivotal-cloud-cache-lookaside-demo/blob/master/2.png)

![](https://github.com/tkaburagi/pivotal-cloud-cache-lookaside-demo/blob/master/3.png)


## Setup
### Creating Service Instances on PCF

MySQL for PCF
```console
$ cf create-service p.mysql db-small  mysql
```

Pivotal Cloud Cache
```console
$ cf create-service p-cloudcache small small-pcc
$ cf create-service-key mypcc-small my-service-key
```

You can retrieve gfsh and pulse end points and credentials by `cf service-key`
```console
$ cf service-key mypcc-small my-service-key
Getting key my-service-key for service instance mypcc-small as tkaburagi@pivotal.io...

{
 "distributed_system_id": "0",
 "locators": [
  "192.168.12.35[55221]",
  "192.168.12.34[55221]",
  "192.168.12.37[55221]"
 ],
 "urls": {
  "gfsh": "************",
  "pulse": "************"
 },
 "users": [
  {
   "password": "************",
   "roles": [
    "cluster_operator"
   ],
   "username": "************"
  },
  {
   "password": "************",
   "roles": [
    "developer"
   ],
   "username": "************"
  }
 ],
 "wan": {
  "sender_credentials": {
   "active": {
    "password": "************",
    "username": "************"
   }
  }
 }
}
```

Creating region on PCC
```console
$ gfsh
$ gfsh> connect --use-http=true --use-ssl --url=<GFSH_URL> --user=c=***** --password=*****
$ gfsh> create region --name=book --type=REPLICATE
```

cf push
```console
$ git clone https://github.com/tkaburagi/pivotal-cloud-cache-lookaside-demo
$ cd pivotal-cloud-cache-lookaside-demo
$ cf push
```
