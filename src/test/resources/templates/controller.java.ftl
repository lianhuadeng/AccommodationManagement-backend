package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplusdemo.common.JsonResponse;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};


/**
 *
 *  前端控制器
 *
 *
 * @author ${author}
 * @since ${date}
 * @version v1.0
 */
@RestController
@RequestMapping("/api/${table.entityPath}")
public class ${table.controllerName} {

    private final Logger logger = LoggerFactory.getLogger( ${table.controllerName}.class );

    @Autowired
    private I${entity}Service ${entity?uncap_first}Service;


    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse<${entity}> getById(@PathVariable("id") Long id)throws Exception {
        ${entity} ${entity?uncap_first} = ${entity?uncap_first}Service.getById(id);
        return JsonResponse.success(${entity?uncap_first});
    }
}

