1. 创建索引

   ```
   curl --location --request PUT 'http://vpc-hk-trade-sf5maknfqzank7flobksfuoswu.ap-east-1.es.amazonaws.com/person-v1' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "settings": {
           "number_of_shards": 3,
           "number_of_replicas": 3,
           "analysis": {
               "normalizer": {
                   "lowercase_normalizer": {
                       "type": "custom",
                       "filter": [
                           "lowercase",
                           "asciifolding"
                       ]
                   }
               }
           }
       },
       "mappings": {
           "properties": {
               "action": {
                   "type": "text",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "activeStatus": {
                   "type": "text",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "asFirstName": {
                   "type": "keyword",
                   "normalizer": "lowercase_normalizer",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "asLastName": {
                   "type": "keyword",
                   "normalizer": "lowercase_normalizer",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "asMiddleName": {
                   "type": "keyword",
                   "normalizer": "lowercase_normalizer",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "birthDate": {
                   "type": "keyword"
               },
               "birthDateYear": {
                   "type": "text",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "birthPlace": {
                   "type": "keyword",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "changeTime": {
                   "type": "date",
                   "format": "yyyy-MM-dd",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "citizenship": {
                   "type": "text",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "deceased": {
                   "type": "text",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "drivingLicenceNo": {
                   "type": "text",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "gender": {
                   "type": "text",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "id": {
                   "type": "long"
               },
               "nationalId": {
                   "type": "text",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "originalScriptName": {
                   "type": "keyword",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "osnc": {
                   "type": "long"
               },
               "passportNo": {
                   "type": "keyword",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "primaryFirstName": {
                   "type": "keyword",
                   "normalizer": "lowercase_normalizer",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "primaryLastName": {
                   "type": "keyword",
                   "normalizer": "lowercase_normalizer",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "primaryMiddleName": {
                   "type": "keyword",
                   "normalizer": "lowercase_normalizer",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "residentCountry": {
                   "type": "keyword",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "sanctionType": {
                   "type": "text",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "singleScriptName": {
                   "type": "keyword",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "singleStringName": {
                   "type": "keyword",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "socialSecurityNo": {
                   "type": "text",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "spellFirstName": {
                   "type": "keyword",
                   "normalizer": "lowercase_normalizer",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "spellLastName": {
                   "type": "keyword",
                   "normalizer": "lowercase_normalizer",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               },
               "spellMiddleName": {
                   "type": "keyword",
                   "normalizer": "lowercase_normalizer",
                   "fields": {
                       "keyword": {
                           "type": "keyword",
                           "ignore_above": 256
                       }
                   }
               }
           }
       }
   }'
   ```

2. 重建索引

   ```
   curl --location --request POST 'http://vpc-hk-trade-sf5maknfqzank7flobksfuoswu.ap-east-1.es.amazonaws.com/_reindex?wait_for_completion=false' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "source": {
           "index": "person"
       },
       "dest": {
           "index": "person-v1"
       }
   }'
   ```

3. 删除索引

   ```
   curl --location --request DELETE 'http://vpc-hk-trade-sf5maknfqzank7flobksfuoswu.ap-east-1.es.amazonaws.com/person'
   ```

4. 为索引建别名

   ```
   curl --location --request POST 'http://vpc-hk-trade-sf5maknfqzank7flobksfuoswu.ap-east-1.es.amazonaws.com/_aliases?pretty' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "actions": [
           {
               "add": {
                   "index": "person-v1",
                   "alias": "person"
               }
           }
       ]
   }'
   ```

   