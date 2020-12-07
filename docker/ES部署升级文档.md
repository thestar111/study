1. 创建ES索引

   ```
   curl --location --request PUT 'http://IP:9200/person' \
   		--header 'Content-Type: application/json' \
       --data-raw '{
           "settings": {
               "number_of_shards": 6,
               "number_of_replicas": 6
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
                       "fields": {
                           "keyword": {
                               "type": "keyword",
                               "ignore_above": 256
                           }
                       }
                   },
                   "asLastName": {
                       "type": "keyword",
                       "fields": {
                           "keyword": {
                               "type": "keyword",
                               "ignore_above": 256
                           }
                       }
                   },
                   "asMiddleName": {
                       "type": "keyword",
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
                       "fields": {
                           "keyword": {
                               "type": "keyword",
                               "ignore_above": 256
                           }
                       }
                   },
                   "primaryLastName": {
                       "type": "keyword",
                       "fields": {
                           "keyword": {
                               "type": "keyword",
                               "ignore_above": 256
                           }
                       }
                   },
                   "primaryMiddleName": {
                       "type": "keyword",
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
                       "fields": {
                           "keyword": {
                               "type": "keyword",
                               "ignore_above": 256
                           }
                       }
                   },
                   "spellLastName": {
                       "type": "keyword",
                       "fields": {
                           "keyword": {
                               "type": "keyword",
                               "ignore_above": 256
                           }
                       }
                   },
                   "spellMiddleName": {
                       "type": "keyword",
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

2. 同步数到ES

   ```
   curl --location --request POST 'http://IP:9008/webull-canal/sync/byTable' \
         --header 'Content-Type: application/json' \
         --data-raw '{
             "stepSize" : 5000,
             "database" : "wl_aml_hk",
             "table" : "t_aml_person"
         }'
   ```

3. 数据库验证

   ```
   SELECT COUNT(1) FROM wl_aml_hk.t_aml_person; -- 2640752
   SELECT COUNT(1) FROM wl_aml_hk.t_aml_entity; -- 179425
   ```

4. ES数据验证

   ```
   curl --location --request GET 'http://vpc-hk-trade-sf5maknfqzank7flobksfuoswu.ap-east-1.es.amazonaws.com/person/_count'
   ```

