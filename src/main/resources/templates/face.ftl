<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>


<@c.page>
    <h3 align="center" style="color: #ffcd30;"><@spring.message "page.face1"/></h3>
    <h3 align="center" style="color: #ffcd30;"><@spring.message "page.face2"/></h3>
    <p></p>
    <p></p>
    <div class="card-deck">
        <div class="card border-warning" style="width: 18rem; background-color: #2b2b2b">
            <div class="card-body">
                <h5 class="card-title" style="color: #ffcd30;"><@spring.message "face.pickup"/></h5>
                <p class="card-text" style="color: #ffcd30;"><@spring.message "face.pickup.text"/></p>
            </div>
        </div>
        <div class="card border-warning" style="width: 18rem; background-color: #2b2b2b">
            <div class="card-body">
                <h5 class="card-title" style="color: #ffcd30;"><@spring.message "face.recommendation"/></h5>
                <p class="card-text" style="color: #ffcd30;"><@spring.message "face.recommendation.text"/></p>
            </div>
        </div>
        <div class="card border-warning" style="width: 18rem; background-color: #2b2b2b">
            <div class="card-body">
                <h5 class="card-title" style="color: #ffcd30;"><@spring.message "face.create"/></h5>
                <p class="card-text" style="color: #ffcd30;"><@spring.message "face.create.text"/></p>
            </div>
        </div>
    </div>
    <p></p>
    <p></p>
    <div class="card-deck">
        <div class="card border-warning" style="width: 18rem; background-color: #2b2b2b">
            <div class="card-body">
                <h5 class="card-title" style="color: #ffcd30;"><@spring.message "face.filter"/></h5>
                <p class="card-text" style="color: #ffcd30;"><@spring.message "face.filter.text"/></p>
            </div>
        </div>
        <div class="card border-warning" style="width: 18rem; background-color: #2b2b2b">
            <div class="card-body">
                <h5 class="card-title" style="color: #ffcd30;"><@spring.message "face.profile"/></h5>
                <p class="card-text" style="color: #ffcd30;"><@spring.message "face.profile.text"/></p>
            </div>
        </div>
        <div class="card border-warning" style="width: 18rem; background-color: #2b2b2b">
            <div class="card-body">
                <h5 class="card-title" style="color: #ffcd30;"><@spring.message "face.rate"/></h5>
                <p class="card-text" style="color: #ffcd30;"><@spring.message "face.rate.text"/></p>
            </div>
        </div>
    </div>
</@c.page>
