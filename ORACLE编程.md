## ORACLE编程整理

###### 定义变量：
	--动态游标
    TYPE PRO_VIR_CURSOR IS REF CURSOR;
    PRO_CUR        PRO_VIR_CURSOR;
    -- 定义集合类型
    ROW_ID_TABLE1  DBMS_SQL.UROWID_TABLE;
    PRO_MAP_TABLE2 DBMS_SQL.VARCHAR2_TABLE;
    MAXROWS        NUMBER DEFAULT 5000;


###### 打开游标并更新数据：

	V_SQL := 'select rid, PRODUCTMAPPEDID from t_opcenter_product_maping order by rid';
    OPEN PRO_CUR FOR V_SQL;
    LOOP
      FETCH PRO_CUR BULK COLLECT
        INTO ROW_ID_TABLE1, PRO_MAP_TABLE2 LIMIT MAXROWS;
      EXIT WHEN ROW_ID_TABLE1.COUNT = 0;
      FORALL I IN 1 .. ROW_ID_TABLE1.COUNT
        UPDATE T_OPCENTER_SOURCEBILLRESULT T
           SET T.PRODUCTNUMBER = PRO_MAP_TABLE2(I),
               T.MEMO          = T.MEMO || ', 更新产品编号为映射产品编号 '
         WHERE ROWID = ROW_ID_TABLE1(I);
      COMMIT;
    END LOOP;
    CLOSE PRO_CUR;

###### 注意：
	BULK COLLECT INTO  指是一个成批聚合类型, 简单的来说 , 它可以存储一个多行多列存储类型 ,into 后面指定从哪里来































































