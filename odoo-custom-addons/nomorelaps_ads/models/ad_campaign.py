from odoo import models, fields, api

class AdCampaign(models.Model):
    _name = 'nomorelaps.ad.campaign'
    _description = 'Campaña Publicitaria NoMoreLaps'
    _inherit = ['mail.thread', 'mail.activity.mixin']

    name = fields.Char(string='Nombre de la Campaña', required=True, tracking=True)
    advertiser_id = fields.Many2one('res.partner', string='Anunciante', required=True, tracking=True)
    ad_type = fields.Selection([
        ('banner', 'Banner Superior'),
        ('featured', 'Parking Destacado'),
        ('push', 'Notificación Push'),
    ], string='Tipo de Anuncio', default='banner', required=True)
    
    start_date = fields.Date(string='Fecha Inicio', required=True)
    end_date = fields.Date(string='Fecha Fin', required=True)
    
    image_url = fields.Char(string='URL de la Imagen')
    target_url = fields.Char(string='URL de Destino')
    
    state = fields.Selection([
        ('draft', 'Borrador'),
        ('active', 'Activo'),
        ('expired', 'Expirado'),
    ], string='Estado', default='draft', tracking=True)

    price = fields.Float(string='Precio Mensual', required=True)
    total_cost = fields.Float(string='Coste Total', compute='_compute_total_cost', store=True)

    @api.depends('start_date', 'end_date', 'price')
    def _compute_total_cost(self):
        for record in self:
            if record.start_date and record.end_date:
                # Lógica simple de cálculo de días
                days = (record.end_date - record.start_date).days
                record.total_cost = (days / 30.0) * record.price
            else:
                record.total_cost = 0.0

    def action_confirm(self):
        self.state = 'active'

    def action_expire(self):
        self.state = 'expired'

    @api.model
    def cron_update_expired_ads(self):
        """Busca anuncios activos cuya fecha de fin ya haya pasado y los marca como expirados"""
        today = fields.Date.today()
        expired_ads = self.search([
            ('state', '=', 'active'),
            ('end_date', '<', today)
        ])
        expired_ads.write({'state': 'expired'})
