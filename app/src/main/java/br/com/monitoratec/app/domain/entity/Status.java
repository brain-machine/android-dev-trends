package br.com.monitoratec.app.domain.entity;

/**
 * Entidade da API GitHub Status.
 *
 * @see <a href="https://status.github.com/api/last-message.json">Last Message</a>
 *
 * Created by falvojr on 1/9/17.
 */
public class Status {
    public String status;
    public String body;
    public String created_on;
}
